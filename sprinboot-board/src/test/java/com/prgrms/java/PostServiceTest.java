package com.prgrms.java;

import com.prgrms.java.dto.CreatePostRequest;
import com.prgrms.java.domain.HobbyType;
import com.prgrms.java.domain.Post;
import com.prgrms.java.domain.User;
import com.prgrms.java.dto.GetPostDetailsResponse;
import com.prgrms.java.dto.GetPostsResponse;
import com.prgrms.java.dto.ModifyPostRequest;
import com.prgrms.java.repository.PostRepository;
import com.prgrms.java.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @DisplayName("게시글을 페이징 조회할 수 있다.")
    @Test
    void getPostsTest() {
        // given
        User user = new User("이택승", 25, HobbyType.MOVIE);
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "데브코스 짱짱", "데브코스 짱짱입니다.", user));
        posts.add(new Post(2L, "데브코스 짱짱2", "데브코스 짱짱2입니다.", user));
        GetPostsResponse expected = new GetPostsResponse(
                posts.stream()
                        .map(GetPostDetailsResponse::from)
                        .toList()
        );
        given(postRepository.findAll(any(PageRequest.class))).willReturn(new PageImpl<Post>(posts));


        // when
        GetPostsResponse actual = postService.getPosts(PageRequest.of(0, 10));

        // then
        assertThat(actual.postDetails())
                .containsAll(expected.postDetails());
    }

    @DisplayName("게시글을 단건 조회할 수 있다.")
    @Test
    void getPostDetailTest() {
        // given
        User user = new User("이택승", 25, HobbyType.MOVIE);
        Post post = new Post(1L, "데브코스 짱짱2", "데브코스 짱짱2입니다.", user);
        GetPostDetailsResponse expected = GetPostDetailsResponse.from(post);
        given(postRepository.findById(any())).willReturn(Optional.ofNullable(post));

        // when
        GetPostDetailsResponse actual = postService.getPostDetail(post.getId());

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("게시글을 등록할 수 있다.")
    @Test
    void createPostTest() {
        // given
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        User user = new User(1L, "이택승", 25, HobbyType.MOVIE);
        CreatePostRequest createPostRequest = new CreatePostRequest(
                "데브코스 짱짱",
                "데브코스 짱짱입니다.",
                user.getId()
        );
        Post post = new Post(1L, createPostRequest.title(), createPostRequest.content(), user);

        given(userRepository.findById(any())).willReturn(Optional.of(user));

        given(postRepository.save(any())).willReturn(post);

        // when
        postService.createPost(createPostRequest);

        // then
        verify(postRepository).save(postArgumentCaptor.capture());
    }

    @DisplayName("게시글을 수정할 수 있다.")
    @Test
    void modifyPostTest() {
        // given
        User user = new User(1L, "이택승", 25, HobbyType.MOVIE);
        Post post = new Post(1L, "데브코스 짱짱", "데브코스 짱짱입니다.", user);

        ModifyPostRequest request = new ModifyPostRequest("데브코스 좋아", "데브코스 좋아용!");

        given(postRepository.findById(any())).willReturn(Optional.of(post));

        // when
        postService.modifyPost(post.getId(), request);

        // then
        assertThat(post.getContent()).isEqualTo(request.content());
        assertThat(post.getTitle()).isEqualTo(request.title());
    }
}