package com.dojinyou.devcourse.boardjpa.post.service;

import com.dojinyou.devcourse.boardjpa.common.exception.NotFoundException;
import com.dojinyou.devcourse.boardjpa.post.entity.Post;
import com.dojinyou.devcourse.boardjpa.post.respository.PostRepository;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostCreateDto;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostResponseDto;
import com.dojinyou.devcourse.boardjpa.user.entity.User;
import com.dojinyou.devcourse.boardjpa.user.service.UserService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PostDefaultServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PostDefaultService postDefaultService;

    private final User savedUser = new User.Builder().id(1L)
                                                     .name("dojin")
                                                     .age(29)
                                                     .hobby("sdoku")
                                                     .build();

    private final String testTitle = "test title";
    private final String testContent = "test content";

    @Nested
    class 게시물_생성_메소드는 {

        @Nested
        class 게시물생성객체가_빈_객체로_들어온다면 {

            @ParameterizedTest(name = "{displayName} = {0}")
            @NullSource
            void 예외를_발생시킨다(PostCreateDto postCreateDto) {

                Throwable throwable = catchThrowable(
                        () -> postDefaultService.create(savedUser.getId(), postCreateDto));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        class Null_필드를_포함한_게시물생성객체가_들어온다면 {
            PostCreateDto nullTitlePostCreateDto = new PostCreateDto.Builder().title(null)
                                                                              .content(testContent)
                                                                              .build();
            PostCreateDto nullContentPostCreateDto = new PostCreateDto.Builder().title(testTitle)
                                                                                .content(null)
                                                                                .build();

            @Test
            void 예외를_발생시킨다_Null_Title() {

                Throwable throwable = catchThrowable(
                        () -> postDefaultService.create(savedUser.getId(), nullTitlePostCreateDto));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(IllegalArgumentException.class);

            }

            @Test
            void 예외를_발생시킨다_Null_Content() {

                Throwable throwable = catchThrowable(
                        () -> postDefaultService.create(savedUser.getId(), nullContentPostCreateDto));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(IllegalArgumentException.class);

            }
        }

        @Nested
        class 비정상적인_필드값을_포함한_게시물생성객체가_들어온다면 {

            @ParameterizedTest(name = "{displayName} title:{0}")
            @EmptySource
            @ValueSource(strings = {" ", "\t", "\n", "         ",
                    "50글자를 넘어가는 엄청나게 길게 작성된 title 012345678901234567890123456789"})
            void 예외를_발생시킨다_Title(String title) {
                PostCreateDto invalidTitlePostCreateDto = new PostCreateDto.Builder().title(title)
                                                                                     .content(testContent)
                                                                                     .build();
                when(userService.findById(savedUser.getId())).thenReturn(savedUser);

                Throwable throwable = catchThrowable(
                        () -> postDefaultService.create(savedUser.getId(), invalidTitlePostCreateDto));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(IllegalArgumentException.class);

            }

            @ParameterizedTest(name = "{displayName} content:{0}")
            @EmptySource
            @ValueSource(strings = {" ", "\t", "\n", "         "})
            void 예외를_발생시킨다_Content(String content) {
                PostCreateDto invalidContentPostCreateDto = new PostCreateDto.Builder().title(testTitle)
                                                                                       .content(content)
                                                                                       .build();

                Throwable throwable = catchThrowable(
                        () -> postDefaultService.create(savedUser.getId(), invalidContentPostCreateDto));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(IllegalArgumentException.class);

            }

            @ParameterizedTest(name = "{displayName} userId:{0}")
            @ValueSource(longs = {Long.MIN_VALUE, -1L, 0L})
            void 예외를_발생시킨다_user_id(long invalidId) {
                PostCreateDto validPostCreateDto = new PostCreateDto.Builder().title(testTitle)
                                                                              .content(testContent)
                                                                              .build();

                Throwable throwable = catchThrowable(
                        () -> postDefaultService.create(invalidId, validPostCreateDto));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        class 정상적인_게시물생성객체가_들어온다면 {
            PostCreateDto postCreateDto = new PostCreateDto.Builder().title(testTitle)
                                                                     .content(testContent)
                                                                     .build();

            @Test
            void UserRepository의_save함수를_호출한다() {
                when(userService.findById(savedUser.getId())).thenReturn(savedUser);

                postDefaultService.create(savedUser.getId(), postCreateDto);

                verify(postRepository, atLeastOnce()).save(any());
            }
        }
    }
    @Nested
    class 아이디를_이용한_게시물_조회시 {

        @Nested
        class 아이디_값이_비정상적일_경우 {

            @ParameterizedTest(name = "{displayName} inputId:{0}")
            @ValueSource(longs = {-1, 0})
            void 아이디값_범위_밖일_때_IllegalArgumentException을_발생시킨다(long inputId) {

                Throwable throwable = catchThrowable(() -> postDefaultService.findById(inputId));

                verify(postRepository, never()).findById(anyLong());
                assertThat(throwable).isNotNull()
                                     .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        class 입력된_아이디_값을_가진_자원이_존재하지_않을_경우 {
            @ParameterizedTest(name = "{displayName} inputId:{0}")
            @ValueSource(longs = {1, 10})
            void NotFoundException을_발생시킨다(long inputId) {
                when(postRepository.findById(inputId)).thenThrow(NotFoundException.class);

                Throwable throwable = catchThrowable(() -> postDefaultService.findById(inputId));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(NotFoundException.class);
            }
        }

        @Nested
        class 입력된_아이디_값을_가진_자원이_존재하는_경우 {

            @Test
            void 해당_자원을_ResponseDto로_변환하여_반환한다() {
                long inputId = 1L;
                LocalDateTime localDateTime = LocalDateTime.of(2022, 5, 24,
                                                               10, 38, 0
                                                              );
                Post foundPost = new Post.Builder().id(inputId)
                                                   .title(testTitle)
                                                   .content(testContent)
                                                   .user(savedUser)
                                                   .build();
                when(postRepository.findById(inputId)).thenReturn(Optional.of(foundPost));

                PostResponseDto postResponseDto = postDefaultService.findById(inputId);

                assertThat(postResponseDto).isNotNull();
                assertThat(postResponseDto.getId()).isEqualTo(inputId);
            }
        }
    }

}