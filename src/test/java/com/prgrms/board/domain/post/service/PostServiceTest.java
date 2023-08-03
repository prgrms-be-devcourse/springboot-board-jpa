package com.prgrms.board.domain.post.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.prgrms.board.domain.post.dto.request.PostCreateRequest;
import com.prgrms.board.domain.post.dto.request.PostUpdateRequest;
import com.prgrms.board.domain.post.dto.response.PostDetailResponse;
import com.prgrms.board.domain.post.dto.response.PostResponse;
import com.prgrms.board.domain.post.entity.Post;
import com.prgrms.board.domain.post.exception.PostNotFoundException;
import com.prgrms.board.domain.post.repository.PostRepository;
import com.prgrms.board.domain.user.entity.User;
import com.prgrms.board.domain.user.service.UserService;
import com.prgrms.board.global.common.dto.PageResponse;
import com.prgrms.board.support.PostFixture;
import com.prgrms.board.support.UserFixture;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    private User user;

    @BeforeEach
    void init() {
        user = UserFixture.user().build();
    }

    @Test
    @DisplayName("게시물 생성에 성공한다.")
    void create_post_success() {
        // given
        Long userId = 1L;
        Post post = PostFixture.post().build();
        PostCreateRequest request = new PostCreateRequest(userId, "제목", "내용");

        given(userService.findUserOrThrow(userId)).willReturn(user);
        given(postRepository.save(any(Post.class))).willReturn(post);

        // when
        PostResponse result = postService.createPost(request);

        // then
        assertThat(result.title()).isEqualTo("제목");
        assertThat(result.content()).isEqualTo("내용");
    }

    @Test
    @DisplayName("전체 게시물 조회에 성공한다.")
    void get_posts_success() {
        // given
        Post post1 = PostFixture.post().title("제목1").content("내용1").build();
        Post post2 = PostFixture.post().title("제목2").content("내용2").build();
        Post post3 = PostFixture.post().title("제목3").content("내용3").build();

        List<Post> posts = List.of(post1, post2, post3);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Post> page = new PageImpl<>(posts.subList(0, 2), pageable, posts.size());

        given(postRepository.findAll(any(Pageable.class))).willReturn(page);

        // when
        PageResponse<Post> result = postService.getPosts(pageable);

        // then
        assertThat(result.totalCount()).isEqualTo(3);
        assertThat(result.totalPage()).isEqualTo(2);
        assertThat(result.items()).hasSize(2);
    }

    @Test
    @DisplayName("특정 게시물 조회에 성공한다.")
    void get_post_success() {
        // given
        Long postId = 1L;
        Post post = PostFixture.post().id(postId).build();
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        // when
        PostDetailResponse postDetailResponse = postService.getPost(postId);

        // then
        assertThat(postDetailResponse.title()).isEqualTo(post.getTitle());
        assertThat(postDetailResponse.content()).isEqualTo(post.getContent());
    }

    @Test
    @DisplayName("게시물 수정에 성공한다.")
    void update_post_success() {
        // given
        Long postId = 1L;
        Post post = PostFixture.post().id(postId).build();
        PostUpdateRequest request = new PostUpdateRequest("수정한 제목", "수정한 내용");

        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        // when
        PostResponse result = postService.updatePost(postId, request);

        // then
        assertThat(result.title()).isEqualTo("수정한 제목");
        assertThat(result.content()).isEqualTo("수정한 내용");
    }

    @Test
    @DisplayName("존재하는 게시물이 없는 경우 예외가 발생한다.")
    void get_post_not_found() {
        // given
        Long postId = 1L;
        given(postRepository.findById(postId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postService.getPost(postId))
            .isInstanceOf(PostNotFoundException.class)
            .hasMessage("존재하지 않는 게시물입니다.");
    }
}
