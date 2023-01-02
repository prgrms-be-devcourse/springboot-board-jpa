package com.programmers.kwonjoosung.board.service;

import com.programmers.kwonjoosung.board.model.Post;
import com.programmers.kwonjoosung.board.model.User;
import com.programmers.kwonjoosung.board.model.dto.CreatePostRequest;
import com.programmers.kwonjoosung.board.model.dto.PostInfo;
import com.programmers.kwonjoosung.board.model.dto.UpdatePostRequest;
import com.programmers.kwonjoosung.board.repository.PostRepository;
import com.programmers.kwonjoosung.board.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    private final PostInfo postInfoInstance
            = new PostInfo("테스트", "테스트 중", "테스터1", LocalDateTime.now());

    @Mock
    private Post post;

    @Mock
    private User user;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    private PostService postService;

    @BeforeEach
    void init() {
        postService = new PostServiceImpl(postRepository, userRepository);
    }

    @Test
    @DisplayName("[성공] 포스트 생성하기")
    void createPostTest() {
        // given
        CreatePostRequest request = mock(CreatePostRequest.class);
        given(request.toEntity()).willReturn(post);
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(user.getId()).willReturn(1L);
        given(postRepository.save(post)).willReturn(post);
        // when
        postService.createPost(user.getId(), request);
        // then
        then(postRepository).should().save(post);
    }

    @Test
    @DisplayName("[성공] 유저 아이디로 조회하기")
    void findPostByUserIdTest() {
        // given
        given(postRepository.findByUserId(1L)).willReturn(List.of(postInfoInstance));
        // when
        postService.findPostByUserId(1L);
        // then
        then(postRepository).should().findByUserId(1L);
    }

    @Test
    @DisplayName("[성공] 포스트 아이디로 단건 조회하기")
    void findPostByPostIdTest() {
        try (MockedStatic<PostInfo> postInfo = mockStatic(PostInfo.class)) {
            // given
            given(postRepository.findById(1L)).willReturn(Optional.of(post));
            given(PostInfo.of(post)).willReturn(postInfoInstance);
            // when
            postService.findPostByPostId(1L);
        }
        // then
        then(postRepository).should().findById(1L);
    }

    @Test
    @DisplayName("[성공] 모든 포스트 조회하기")
    void findAllTest() {
        try (MockedStatic<PostInfo> postInfo = mockStatic(PostInfo.class)) {
            // given
            Post post1 = new Post("테스트 1", "테스트 1번");
            Post post2 = new Post("테스트 2", "테스트 2번");
            given(postRepository.findAll()).willReturn(List.of(post1, post2));
            given(PostInfo.of(post1)).willReturn(postInfoInstance);
            given(PostInfo.of(post2)).willReturn(postInfoInstance);
            // when
            postService.findAllPost();
        }
        // then
        then(postRepository).should().findAll();
    }

    @Test
    @DisplayName("[성공] 페이지 단위로 포스트 조회하기")
    void findAllWithPageTest() {
        // given
        Pageable pageable = PageRequest.of(1, 10);
        List<Post> posts = new ArrayList<>();
        try (MockedStatic<PostInfo> postInfo = mockStatic(PostInfo.class)) {
            for (int i = 0; i < 10; i++) {
                posts.add(post);
            }
            given(postRepository.findAll(pageable)).willReturn(new PageImpl<>(posts));
            // when
            postService.findAllPost(pageable);
        }
        // then
        then(postRepository).should().findAll(pageable);
    }


    @Test
    @DisplayName("[성공] 포스트 수정하기")
    void updatePostTest() {
        // given
        UpdatePostRequest request = mock(UpdatePostRequest.class);
        Post post = new Post("테스트", "테스트 중");
        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        given(request.getTitle()).willReturn("수정 테스트");
        given(request.getContent()).willReturn("수정 테스트 중");
        try (MockedStatic<PostInfo> postInfo = mockStatic(PostInfo.class)) {
            given(PostInfo.of(post)).willReturn(postInfoInstance);
            // when
            postService.updatePost(1L, request);
        }

        // then
        then(postRepository).should().findById(1L);
        assertThat(post)
                .hasFieldOrPropertyWithValue("title", "수정 테스트")
                .hasFieldOrPropertyWithValue("content", "수정 테스트 중");
    }

}