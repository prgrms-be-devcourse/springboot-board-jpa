package com.programmers.board.core.post.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.programmers.board.core.post.application.dto.PostCreateRequest;
import com.programmers.board.core.post.application.dto.PostResponse;
import com.programmers.board.core.post.application.dto.PostUpdateRequest;
import com.programmers.board.core.post.domain.Post;
import com.programmers.board.core.post.domain.repository.PostRepository;
import com.programmers.board.core.user.domain.Hobby;
import com.programmers.board.core.user.domain.User;
import com.programmers.board.core.user.domain.repository.UserRepository;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

  @InjectMocks
  private PostService postService;

  @Mock
  private PostRepository postRepository;

  @Mock
  private UserRepository userRepository;

  private User user = User.builder()
      .name("jung")
      .age(145)
      .hobby(Hobby.COOKING)
      .build();

  private Post post = Post.builder()
      .title("title")
      .content("content")
      .build();


  @Nested
  @DisplayName("포스트 저장 save 메소드: ")
  class DescribeSave {

    PostCreateRequest createRequestPost = null;

    @Nested
    @DisplayName("호출 시 null인 dto를 받을 때")
    class ContextReceiveNullPost {

      @Test
      @DisplayName("잘못된 예외 확인")
      void ReceiveNullDto() {
        assertThatThrownBy(() -> postService.save(createRequestPost))
            .isInstanceOf(NullPointerException.class);
      }

    }

    @Nested
    @DisplayName("Create Dto 받을 때: ")
    class ContextReceivePost {

      PostCreateRequest request = PostCreateRequest.builder()
          .title("title")
          .content("content")
          .userId(1L)
          .build();

      PostResponse response = new PostResponse(1L, "title", "content");

      @Test
      @DisplayName("Post respository save 메서드 호출")
      void callRepositorySave() {

        when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(user));
        when(postRepository.save(any())).thenReturn(post);

        postService.save(request);

        verify(postRepository).save(any());
      }
    }

  }

  @Nested
  @DisplayName("포스트 단 건 조회 findOne 메소드: ")
  class DescribeFindOne {

    @Nested
    @DisplayName("id 존재하지 않을때")
    class ContextReceiveInvalidId {

      Long wrongId = -1L;

      @Test
      @DisplayName("EntityNotFoundException 확인")
      void throwEntityNotFound() {
        when(postRepository.findById(wrongId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.findOne(wrongId))
            .isInstanceOf(EntityNotFoundException.class);
      }

    }

    @Nested
    @DisplayName("id 존재 할 때")
    class ContextReceiveValidId {

      Long id = 1L;

      Post post = Post.builder()
          .title("title")
          .content("content")
          .user(new User("jung", 145, Hobby.COOKING))
          .build();

      PostResponse expectedResponse = PostResponse.of(post);

      @Test
      @DisplayName("해당 포스트 반환 확인")
      void returnPost() {
        when(postRepository.findById(id)).thenReturn(Optional.of(post));

        PostResponse actualResponse = postService.findOne(id);

        verify(postRepository).findById(id);
        assertAll(
            () -> assertThat(expectedResponse.getId()).isEqualTo(actualResponse.getId()),
            () -> assertThat(expectedResponse.getTitle()).isEqualTo(actualResponse.getTitle()),
            () -> assertThat(expectedResponse.getContent()).isEqualTo(actualResponse.getContent())
        );
      }
    }

  }


  @Nested
  @DisplayName("포스트 수정 update 메서드:")
  class DescribeUpdate {

    PostUpdateRequest updateRequestPost = PostUpdateRequest.builder()
        .title("update title")
        .content("update content")
        .build();

    @Nested
    @DisplayName("id 존재하지 않을때")
    class ContextReceiveInvalidId {

      Long wrongId = -1L;

      @Test
      @DisplayName("EntityNotFoundException 확인")
      void throwEntityNotFound() {
        assertThatThrownBy(() -> postService.update(wrongId, updateRequestPost))
            .isInstanceOf(EntityNotFoundException.class);
      }

    }

    @Nested
    @DisplayName("id 존재 할 때")
    class ContextReceiveValidId {

      Long id = 1L;

      Post post = Post.builder()
          .title("title")
          .content("content")
          .user(User.builder()
              .name("jung")
              .age(145)
              .hobby(Hobby.READING)
              .build())
          .build();

      @Test
      @DisplayName("수정이 이루어 지는지 확인")
      void updatePost() {

        given(postRepository.findById(id)).willReturn(Optional.of(post));

        PostResponse actualResponsePost = postService.update(id, updateRequestPost);

        assertAll(
            () -> assertThat(actualResponsePost.getTitle()).isEqualTo(updateRequestPost.getTitle()),
            () -> assertThat(actualResponsePost.getContent()).isEqualTo(
                updateRequestPost.getContent())
        );
      }
    }

  }
}