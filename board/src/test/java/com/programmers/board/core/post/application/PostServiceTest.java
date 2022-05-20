package com.programmers.board.core.post.application;

import com.programmers.board.core.post.application.dto.CreateRequestPost;
import com.programmers.board.core.post.application.dto.ResponsePost;
import com.programmers.board.core.post.application.dto.UpdateRequestPost;
import com.programmers.board.core.post.domain.Post;
import com.programmers.board.core.post.domain.repository.PostRepository;
import com.programmers.board.core.user.application.dto.UserDto;
import com.programmers.board.core.user.domain.Hobby;
import com.programmers.board.core.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @Spy
    @InjectMocks
    PostService postService;

    @Nested
    @DisplayName("포스트 저장 save 메소드: ")
    class DescribeSave {

        CreateRequestPost createRequestPost = null;

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

            CreateRequestPost createRequestPost = CreateRequestPost.builder()
                    .title("title")
                    .content("content")
                    .userDto(
                            UserDto.builder()
                                    .id(1L)
                                    .name("jung")
                                    .age(145)
                                    .hobby(Hobby.COOKING)
                                    .build()
                    )
                    .build();

            @Test
            @DisplayName("Post respository save 메서드 호출")
            void callRepositorySave() {

                postService.save(createRequestPost);

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
                    .id(id)
                    .title("title")
                    .content("content")
                    .user(new User(1L, "jung", 145, Hobby.COOKING))
                    .build();

            ResponsePost expectedResponse = ResponsePost.of(post);

            @Test
            @DisplayName("해당 포스트 반환 확인")
            void returnPost() {
                when(postRepository.findById(id)).thenReturn(Optional.of(post));

                ResponsePost actualResponse = postService.findOne(id);

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
    class DescribeUpdate{

        UpdateRequestPost updateRequestPost = UpdateRequestPost.builder()
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

                given(postRepository.findById(wrongId)).willReturn(Optional.empty());

                then(postService).shouldHaveNoInteractions();

                assertThatThrownBy(() -> postService.update(wrongId,updateRequestPost))
                        .isInstanceOf(EntityNotFoundException.class);
            }

        }

        @Nested
        @DisplayName("id 존재 할 때")
        class ContextReceiveValidId{

            Long id = 1L;

            Post post = Post.builder()
                    .id(id)
                    .title("title")
                    .content("content")
                    .user(new User(1L, "jung", 145, Hobby.COOKING))
                    .build();

            @Test
            @DisplayName("수정이 이루어 지는지 확인")
            void updatePost(){

                given(postRepository.findById(1L)).willReturn(Optional.of(post));

                ResponsePost actualResponsePost = postService.update(id,updateRequestPost);

                assertAll(
                        () -> assertThat(actualResponsePost.getId()).isEqualTo(id),
                        () -> assertThat(actualResponsePost.getTitle()).isEqualTo(updateRequestPost.getTitle()),
                        () -> assertThat(actualResponsePost.getContent()).isEqualTo(updateRequestPost.getContent())
                );
            }
        }

    }
}