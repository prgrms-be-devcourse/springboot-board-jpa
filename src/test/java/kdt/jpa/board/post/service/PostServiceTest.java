package kdt.jpa.board.post.service;

import kdt.jpa.board.global.exception.BoardException;
import kdt.jpa.board.post.domain.Post;
import kdt.jpa.board.post.fixture.PostFixture;
import kdt.jpa.board.post.repository.PostRepository;
import kdt.jpa.board.post.service.dto.request.CreatePostRequest;
import kdt.jpa.board.post.service.dto.request.EditPostRequest;
import kdt.jpa.board.post.service.dto.response.PostListResponse;
import kdt.jpa.board.post.service.dto.response.PostResponse;
import kdt.jpa.board.post.service.utils.PostMapper;
import kdt.jpa.board.user.domain.User;
import kdt.jpa.board.user.fixture.UserFixture;
import kdt.jpa.board.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("[PostService 테스트]")
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Nested
    @DisplayName("[게시물을 만든다]")
    class createPost {

        @Test
        @DisplayName("[성공적으로 만든다]")
        void success() {
            //given
            Post post = PostFixture.getPost(1L);
            User user = UserFixture.getUser();
            CreatePostRequest request = new CreatePostRequest(post.getTitle(), post.getContent(), 1L);

            given(postRepository.save(any(Post.class)))
                .willReturn(post);
            given(userRepository.findById(1L))
                .willReturn(Optional.of(user));

            //when
            Long result = postService.createPost(request);

            //then
            assertThat(result).isEqualTo(1L);
        }

        @Test
        @DisplayName("[id 에 맞는 회원이 존재하지 않아 실패한다]")
        void fail() {
            //given
            CreatePostRequest request = new CreatePostRequest("title", "content", 1L);
            given(userRepository.findById(1L))
                .willReturn(Optional.empty());

            //when
            ThrowingCallable when = () -> postService.createPost(request);

            //then
            assertThatThrownBy(when)
                .isInstanceOf(BoardException.class)
                .hasMessageContaining("존재하지 않는 회원입니다");
        }
    }

    @Nested
    @DisplayName("[게시물을 id로 단건 조회한다]")
    class getById {

        @Test
        @DisplayName("[성공적으로 조회한다]")
        void success() {
            //given
            Post post = PostFixture.getPost();
            given(postRepository.findById(1L))
                .willReturn(Optional.of(post));

            //when
            PostResponse postResponse = postService.getById(1L);

            //then
            assertAll(
                () -> assertThat(postResponse.title()).isEqualTo(post.getTitle()),
                () -> assertThat(postResponse.content()).isEqualTo(post.getContent()),
                () -> assertThat(postResponse.userName()).isEqualTo(post.getUserName())
            );
        }

        @Test
        @DisplayName("[id 에 맞는 게시물이 존재하지 않아 실패한다]")
        void fail() {
            //given
            given(postRepository.findById(1L))
                .willReturn(Optional.empty());

            //when
            ThrowingCallable when = () -> postService.getById(1L);

            //then
            assertThatThrownBy(when)
                .isInstanceOf(BoardException.class)
                .hasMessageContaining("존재하지 않는 포스트입니다");
        }
    }

    @Test
    @DisplayName("게시물을 페이징하고 조회한다")
    void getPosts() {
        //given
        Pageable pageable = PageRequest.of(0, 5);
        List<Post> posts = List.of(PostFixture.getPost(), PostFixture.getPost());
        Page<Post> pagedPost = new PageImpl<>(posts);
        given(postRepository.findAll(pageable))
            .willReturn(pagedPost);

        //when
        PostListResponse result = postService.getPosts(pageable);

        //then
        Page<PostResponse> responses = result.responses();
        assertAll(
            () -> assertThat(responses.getTotalPages()).isEqualTo(pagedPost.getTotalPages()),
            () -> assertThat(responses.getTotalElements()).isEqualTo(pagedPost.getTotalElements()),
            () -> assertThat(responses.getNumber()).isEqualTo(pagedPost.getNumber()),
            () -> {
                List<PostResponse> postResponses = posts.stream().map(PostMapper::toPostResponse).toList();
                assertThat(responses.getContent()).containsAll(postResponses);
            }
        );
    }

    @Nested
    @DisplayName("[게시물을 수정한다]")
    class editPost {

        @Test
        @DisplayName("[성공적으로 수정한다]")
        void success() {
            //given
            Post post = PostFixture.getPost(1L);
            EditPostRequest request = new EditPostRequest(post.getId(), post.getTitle(), post.getContent());

            given(postRepository.findById(request.postId()))
                .willReturn(Optional.of(post));

            //when
            boolean edited = postService.editPost(request);

            //then
            assertThat(edited).isTrue();
        }

        @Test
        @DisplayName("[id 에 맞는 게시물이 존재하지 않아 실패한다]")
        void fail() {
            //given
            Post post = PostFixture.getPost(1L);
            EditPostRequest editPostRequest = new EditPostRequest(post.getId(), post.getTitle(), post.getContent());

            given(postRepository.findById(1L))
                .willReturn(Optional.empty());

            //when
            ThrowingCallable when = () -> postService.editPost(editPostRequest);

            //then
            assertThatThrownBy(when)
                .isInstanceOf(BoardException.class)
                .hasMessageContaining("존재하지 않는 포스트입니다");
        }
    }
}
