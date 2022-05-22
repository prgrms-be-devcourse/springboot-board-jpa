package com.kdt.prgrms.board.post;

import com.kdt.prgrms.board.dto.PostAddRequest;
import com.kdt.prgrms.board.dto.PostResponse;
import com.kdt.prgrms.board.dto.PostUpdateRequest;
import com.kdt.prgrms.board.entity.post.Post;
import com.kdt.prgrms.board.entity.post.PostRepository;
import com.kdt.prgrms.board.entity.user.User;
import com.kdt.prgrms.board.entity.user.UserRepository;
import com.kdt.prgrms.board.exception.custom.AccessDeniedException;
import com.kdt.prgrms.board.exception.custom.NotFoundException;
import com.kdt.prgrms.board.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    PostService postService;

    @Nested
    @DisplayName("addPost 메서드는 테스트할 때")
    class DescribeAddPost {

        @Nested
        @DisplayName("인자로 null을 받으면")
        class ContextReceiveNull {

            final PostAddRequest request = null;

            @Test
            @DisplayName("IllegalArgumentException을 반환한다.")
            void itThrowIllegalArgumentException() {

                Assertions.assertThatThrownBy(() -> postService.addPost(request))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("userId가 저장되어 있는 user정보가 아니라면")
        class ContextNotExistUser {

            final PostAddRequest request = new PostAddRequest(-1, "title", "content");

            @Test
            @DisplayName("NotFoundException을 반환한다.")
            void itThrowNotFoundException() {

                when(userRepository.findById(any(long.class))).thenThrow(NotFoundException.class);

                Assertions.assertThatThrownBy(() ->postService.addPost(request))
                        .isInstanceOf(NotFoundException.class);
            }
        }

        @Nested
        @DisplayName("회원인 user가 게시글 작성 요청을 하면")
        class ContextValidAddPostRequest {

            final PostAddRequest request = new PostAddRequest(1, "title", "content");
            final User user = User.builder().build();

            @Test
            @DisplayName("postRepository의 save메서드를 호출한다.")
            void itCallRepositorySave() {

                when(userRepository.findById(any(long.class))).thenReturn(Optional.of(user));

                postService.addPost(request);

                verify(postRepository).save(request.toEntity(any()));
            }
        }


    }

    @Nested
    @DisplayName("getPosts 메서드는 테스트할 때")
    class DescribeGetPosts {

        @Nested
        @DisplayName("요청을 받으면")
        class ContextCalledThis {

            final User user = User.builder()
                    .name("fofo")
                    .age(1)
                    .build();
            final List<Post> posts = List.of(Post.builder()
                    .title("toto")
                    .content("titi")
                    .user(user)
                    .build()
            );

            @Test
            @DisplayName("postDto 리스트를 반환한다.")
            void itReturnPostDtoList() {

                when(postRepository.findAll()).thenReturn(posts);

                List<PostResponse> actual = postService.getPosts();

                Assertions.assertThat(actual.size()).isEqualTo(1);
            }
        }
    }

    @Nested
    @DisplayName("getPostById 메서드는 테스트할 때")
    class DescribeGetPostById {

        @Nested
        @DisplayName("해당 id의 게시글이 없다면")
        class ContextNotExistPost {

            final long wrongId = -1;

            @Test
            @DisplayName("NotFoundException을 반환한다.")
            void itThrowNotFoundException() {

                when(postRepository.findById(wrongId)).thenReturn(Optional.empty());

                Assertions.assertThatThrownBy(() -> postService.getPostById(wrongId))
                        .isInstanceOf(NotFoundException.class);
            }
        }

        @Nested
        @DisplayName("존재하는 게시글의 id를 인자로 받으면")
        class ContextExistPost {

            final long id = 1;

            final User user = User.builder()
                    .name("fofo")
                    .age(1)
                    .build();

            final Post post = Post.builder()
                    .title("toto")
                    .content("titi")
                    .user(user)
                    .build();

            @Test
            @DisplayName("해당 게시글의 dto를 반환한다.")
            void itReturnPostResponse() {

                when(postRepository.findById(id)).thenReturn(Optional.of(post));

                PostResponse expect = PostResponse.from(post);
                PostResponse actual = postService.getPostById(id);

                Assertions.assertThat(expect.getPostId()).isEqualTo(actual.getPostId());
                Assertions.assertThat(expect.getContent()).isEqualTo(actual.getContent());
                Assertions.assertThat(expect.getTitle()).isEqualTo(actual.getTitle());
            }
        }
    }

    @Nested
    @DisplayName("updatePostById 메서드는 테스트 할 때")
    class DescribeUpdatePostById {

        final User requestUser = User.builder()
                .name("fofo")
                .age(13)
                .build();

        final User postUser = User.builder()
                .name("fafa")
                .age(12)
                .build();

        final Post post = Post.builder()
                .title("title")
                .content("ajaj")
                .user(postUser)
                .build();

        @Nested
        @DisplayName("requestDto가 null이면")
        class ContextReceiveNull {

            final long postId = 1;
            final PostUpdateRequest request = null;

            @Test
            @DisplayName("IllegalArgumentException을 반환한다")
            void itThrowIllegalArgumentException() {

                Assertions.assertThatThrownBy(() -> postService.updatePostById(postId, request))
                        .isInstanceOf(IllegalArgumentException.class);

            }
        }

        @Nested
        @DisplayName("수정 요청자가 존재하지 않는 user면")
        class ContextReceiveWrongUserId {

            final long postId = 1;
            final PostUpdateRequest request = new PostUpdateRequest(-1, "title", "content");

            @Test
            @DisplayName("NotFoundException을 반환한다")
            void itThrowNorFoundException() {

                when(userRepository.findById(request.getUserId())).thenReturn(Optional.empty());

                Assertions.assertThatThrownBy(() -> postService.updatePostById(postId, request))
                        .isInstanceOf(NotFoundException.class);
            }
        }

        @Nested
        @DisplayName("postId가 존재하지 않는 게시물 id면")
        class ContextReceiveWrongPostId {

            final long postId = -1;
            final PostUpdateRequest request = new PostUpdateRequest(1, "title", "content");
            final User user = User.builder()
                    .name("fofo")
                    .age(13)
                    .build();

            @Test
            @DisplayName("NotFoundException을 반환한다")
            void itThrowNorFoundException() {

                when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(user));
                when(postRepository.findById(postId)).thenReturn(Optional.empty());

                Assertions.assertThatThrownBy(() -> postService.updatePostById(postId, request))
                        .isInstanceOf(NotFoundException.class);
            }
        }

        @Nested
        @DisplayName("요청자의 id와 게시물의 userid가 다르다면")
        class ContextAccessDenied {

            final long postId = 1;
            final PostUpdateRequest request = new PostUpdateRequest(1, "title", "content");

            @Test
            @DisplayName("AccessDeniedException을 반환한다.")
            void itThrowAccessDeniedException() {

                ReflectionTestUtils.setField(requestUser, "id", 2);
                ReflectionTestUtils.setField(postUser, "id", 1);

                when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(requestUser));
                when(postRepository.findById(postId)).thenReturn(Optional.of(post));

                Assertions.assertThatThrownBy(() -> postService.updatePostById(postId, request))
                        .isInstanceOf(AccessDeniedException.class);
            }
        }

        @Nested
        @DisplayName("게시물 소유자가 해당 게시물의 제목과 내용을 수정하면")
        class ContextUpdateRequest {

            final long postId = 1;

            final PostUpdateRequest request = new PostUpdateRequest(1, "title", "content");

            @Test
            @DisplayName("수정된 게시물을 저장한다..")
            void itUpdatePost() {

                ReflectionTestUtils.setField(postUser, "id", 1);
                ReflectionTestUtils.setField(requestUser, "id", 1);

                when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(requestUser));
                when(postRepository.findById(postId)).thenReturn(Optional.of(post));

                postService.updatePostById(postId, request);

                verify(postRepository).save(post);
            }
        }
    }
}
