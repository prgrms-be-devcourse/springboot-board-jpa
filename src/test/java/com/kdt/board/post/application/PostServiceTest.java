package com.kdt.board.post.application;

import com.kdt.board.common.exception.PostNotFoundException;
import com.kdt.board.common.exception.UserNotFoundException;
import com.kdt.board.post.application.dto.request.EditPostRequestDto;
import com.kdt.board.post.application.dto.request.WritePostRequestDto;
import com.kdt.board.post.application.dto.response.PostResponseDto;
import com.kdt.board.post.domain.Post;
import com.kdt.board.post.domain.repository.PostRepository;
import com.kdt.board.user.domain.User;
import com.kdt.board.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    PostServiceImpl postService;

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Nested
    @DisplayName("write 메소드는")
    class Describe_write {

        @Nested
        @DisplayName("전달인자가 null이면")
        class Context_with_null_argument {

            @Test
            @DisplayName("IllegalArgumentException이 발생한다.")
            void It_response_IllegalArgumentException() {
                //given
                final WritePostRequestDto writePostRequestDto = null;

                //when, then
                assertThrows(IllegalArgumentException.class, () -> postService.write(writePostRequestDto));
            }
        }

       @Nested
       @DisplayName("해당 id의 유저가 존재하지 않으면")
       class Context_with_not_existed_user {

            @Test
            @DisplayName("UserNotFoundException이 발생한다.")
            void It_response_UserNotFoundException() {
                //given
                final Long userId = 1L;
                final WritePostRequestDto writePostRequestDto = WritePostRequestDto.builder()
                        .userId(userId)
                        .title("test")
                        .content("test")
                        .build();
                doReturn(Optional.empty()).when(userRepository).findById(userId);

                //when, then
                assertThrows(UserNotFoundException.class, () -> postService.write(writePostRequestDto));
            }
       }

       @Nested
       @DisplayName("해당 id의 유저가 존재하면")
       class Context_with_existed_user {

           @Test
           @DisplayName("repository save 메소드를 호출한다.")
           void It_call_save() {
               //given
               final Long userId = 1L;
               final WritePostRequestDto writePostRequestDto = WritePostRequestDto.builder()
                       .userId(userId)
                       .title("test")
                       .content("test")
                       .build();
               final User user = firstUser();
               doReturn(Optional.of(user)).when(userRepository).findById(userId);

               //when
               postService.write(writePostRequestDto);

               //then
               verify(postRepository, times(1)).save(any(Post.class));
           }
       }
    }

    @Nested
    @DisplayName("getAll 메소드는")
    class Describe_getAll {

        @Nested
        @DisplayName("전달인자가 null이면")
        class Context_with_null_argument {

            @Test
            @DisplayName("IllegalArgumentException이 발생한다.")
            void It_response_IllegalArgumentException() {
                //given
                final Pageable pageable = null;

                //when, then
                assertThrows(IllegalArgumentException.class, () -> postService.getAll(pageable));
            }
        }

        @Nested
        @DisplayName("전달인자가 null이 아닌 정상적인 값이면")
        class Context_with_normally_argument {

            @Test
            @DisplayName("repository findAllOrderByCreatedAtDesc 메소드를 호출하고 조회된 목록을 반환한다.")
            void It_call_findAllOrderByCreatedAtDesc_and_response_found_list() {
                //given
                final PageRequest pageRequest = PageRequest.of(0, 10);
                final Post firstPost = Post.builder()
                        .id(1L)
                        .title("first test")
                        .content("first test")
                        .user(firstUser())
                        .build();
                final Post secondPost = Post.builder()
                        .id(2L)
                        .title("second test")
                        .content("second test")
                        .user(secondUser())
                        .build();
                final List<Post> posts = List.of(firstPost, secondPost);
                doReturn(posts).when(postRepository).findAllOrderByCreatedAtDesc(pageRequest);

                //when
                final List<PostResponseDto> foundPosts = postService.getAll(pageRequest);

                //then
                verify(postRepository, times(1)).findAllOrderByCreatedAtDesc(any(Pageable.class));
                assertThat(foundPosts.size()).isEqualTo(2);
            }
        }
    }

    @Nested
    @DisplayName("getOne 메소드는")
    class Describe_getOne {

        @Nested
        @DisplayName("해당 id의 글이 존재하지 않으면")
        class Context_with_not_existed_post {

            @Test
            @DisplayName("PostNotFoundException이 발생한다.")
            void It_response_PostNotFoundException() {
                //given
                final Long id = 1L;
                doReturn(Optional.empty()).when(postRepository).findById(id);

                //when, then
                assertThrows(PostNotFoundException.class, () -> postService.getOne(id));
            }
        }

        @Nested
        @DisplayName("해당 id의 글이 존재하면")
        class Context_with_existed_post {

            @Test
            @DisplayName("repository findById 메소드를 호출하고 조회된 글을 반환한다.")
            void It_call_findById_and_response_found_post() {
                //given
                final Long id = 1L;
                final Post post = Post.builder()
                        .id(id)
                        .title("first test")
                        .content("first test")
                        .user(firstUser())
                        .build();
                final PostResponseDto postResponseDto = PostResponseDto.from(post);

                doReturn(Optional.of(post)).when(postRepository).findById(id);

                //when
                final PostResponseDto foundPostDto = postService.getOne(id);

                //then
                verify(postRepository, times(1)).findById(anyLong());
                assertThat(foundPostDto).usingRecursiveComparison().isEqualTo(postResponseDto);
            }
        }
    }

    @Nested
    @DisplayName("edit 메소드는")
    class Describe_edit {

        @Nested
        @DisplayName("전달인자가 null이면")
        class Context_with_null_argument {

            @Test
            @DisplayName("IllegalArgumentException이 발생한다.")
            void It_response_IllegalArgumentException() {
                //given
                final EditPostRequestDto editPostRequestDto = null;

                //when, then
                assertThrows(IllegalArgumentException.class, () -> postService.edit(editPostRequestDto));
            }
        }

        @Nested
        @DisplayName("해당 id의 글이 존재하지 않으면")
        class Context_with_not_existed_post {

            @Test
            @DisplayName("PostNotFoundException이 발생한다.")
            void It_response_PostNotFoundException() {
                //given
                final Long postId = 1L;
                final EditPostRequestDto editPostRequestDto = EditPostRequestDto.builder()
                        .userId(1L)
                        .postId(postId)
                        .title("update test")
                        .content("update test")
                        .build();
                doReturn(Optional.empty()).when(postRepository).findById(postId);

                //when, then
                assertThrows(PostNotFoundException.class, () -> postService.edit(editPostRequestDto));
            }
        }

        @Nested
        @DisplayName("해당 id의 유저가 존재하지 않으면")
        class Context_with_not_existed_user {

            @Test
            @DisplayName("UserNotFoundException이 발생한다.")
            void It_response_UserNotFoundException() {
                //given
                final Long userId = 1L;
                final Long postId = 1L;
                final EditPostRequestDto editPostRequestDto = EditPostRequestDto.builder()
                        .userId(userId)
                        .postId(1L)
                        .title("update test")
                        .content("update test")
                        .build();
                final Post post = Post.builder()
                        .id(postId)
                        .title("first test")
                        .content("first test")
                        .user(firstUser())
                        .build();
                doReturn(Optional.of(post)).when(postRepository).findById(postId);
                doReturn(Optional.empty()).when(userRepository).findById(userId);

                //when, then
                assertThrows(UserNotFoundException.class, () -> postService.edit(editPostRequestDto));
            }
        }

        @Nested
        @DisplayName("전달인자가 정상적인 값이면")
        class Context_with_normally_editPostRequesetDto {

            @Test
            @DisplayName("글을 수정한다.")
            void It_response_update_post() {
                //given
                final Long userId = 1L;
                final Long postId = 1L;
                final EditPostRequestDto editPostRequestDto = EditPostRequestDto.builder()
                        .userId(userId)
                        .postId(1L)
                        .title("update test")
                        .content("update test")
                        .build();
                final User user = firstUser();
                final Post post = Post.builder()
                        .id(postId)
                        .title("first test")
                        .content("first test")
                        .user(user)
                        .build();
                doReturn(Optional.of(post)).when(postRepository).findById(postId);
                doReturn(Optional.of(user)).when(userRepository).findById(userId);

                //when
                postService.edit(editPostRequestDto);

                //then
                assertThat(post).extracting("title").isEqualTo("update test");
                assertThat(post).extracting("content").isEqualTo("update test");
            }
        }
    }

    private User firstUser() {
        return User.builder()
                .id(1L)
                .name("first test")
                .email("first test")
                .build();
    }

    private User secondUser() {
        return User.builder()
                .id(2L)
                .name("second test")
                .email("second test")
                .build();
    }
}
