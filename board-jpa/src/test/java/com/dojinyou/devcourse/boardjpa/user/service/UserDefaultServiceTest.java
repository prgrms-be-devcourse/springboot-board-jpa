package com.dojinyou.devcourse.boardjpa.user.service;

import com.dojinyou.devcourse.boardjpa.common.exception.NotFoundException;
import com.dojinyou.devcourse.boardjpa.user.entity.User;
import com.dojinyou.devcourse.boardjpa.user.repository.UserRepository;
import com.dojinyou.devcourse.boardjpa.user.service.dto.UserCreateDto;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserDefaultServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDefaultService userDefaultService;


    private final String name = "testName";
    private final Integer age = 20;
    private final String hobby = "testHobby";

    @Nested
    class 유저_생성_메소드는 {

        @Nested
        class 유저생성객체가_빈_객체로_들어온다면 {

            @ParameterizedTest(name = "{displayName} = {0}")
            @NullSource
            void 예외를_발생시킨다(UserCreateDto userCreateDto) {

                Throwable throwable = catchThrowable(() -> userDefaultService.create(userCreateDto));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        class Null_필드를_포함한_유저생성객체가_들어온다면 {
            UserCreateDto nullNameUserCreateDto = new UserCreateDto.Builder().name(null)
                                                                             .age(age)
                                                                             .hobby(hobby)
                                                                             .build();
            UserCreateDto nullHobbyUserCreateDto = new UserCreateDto.Builder().name(name)
                                                                              .age(age)
                                                                              .hobby(null)
                                                                              .build();

            @Test
            void 예외를_발생시킨다_Null_name() {

                Throwable throwable = catchThrowable(() -> userDefaultService.create(nullNameUserCreateDto));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(IllegalArgumentException.class);

            }

            @Test
            void 예외를_발생시킨다_Null_hobby() {

                Throwable throwable = catchThrowable(() -> userDefaultService.create(nullHobbyUserCreateDto));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(IllegalArgumentException.class);

            }
        }
        
        @Nested
        class 비정상적인_필드값을_포함한_유저생성객체가_들어온다면 {

            @ParameterizedTest(name = "{displayName} name:{0}")
            @EmptySource
            @ValueSource(strings = {" ", "\t", "\n", "         "})
            void 예외를_발생시킨다_Name(String name) {
                UserCreateDto invalidNameUserCreateDto = new UserCreateDto.Builder().name(name)
                                                                                    .age(age)
                                                                                    .hobby(hobby)
                                                                                    .build();

                Throwable throwable = catchThrowable(
                        () -> userDefaultService.create(invalidNameUserCreateDto));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(IllegalArgumentException.class);

            }

            @ParameterizedTest(name = "{displayName} age:{0}")
            @ValueSource(ints = {Integer.MIN_VALUE, -1, 0})
            void 예외를_발생시킨다_age(int age) {
                UserCreateDto invalidAgeUserCreateDto = new UserCreateDto.Builder().name(name)
                                                                                    .age(age)
                                                                                    .hobby(hobby)
                                                                                    .build();

                Throwable throwable = catchThrowable(
                        () -> userDefaultService.create(invalidAgeUserCreateDto));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(IllegalArgumentException.class);

            }


            @ParameterizedTest(name = "{displayName} hobby:{0}")
            @EmptySource
            @ValueSource(strings = {" ", "\t", "\n", "         "})
            void 예외를_발생시킨다_hobby(String hobby) {
                UserCreateDto invalidHobbyUserCreateDto = new UserCreateDto.Builder().name(name)
                                                                                    .age(age)
                                                                                    .hobby(hobby)
                                                                                    .build();

                Throwable throwable = catchThrowable(() -> userDefaultService.create(invalidHobbyUserCreateDto));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(IllegalArgumentException.class);

            }
        }

        @Nested
        class 정상적인_유저생성객체가_들어온다면 {
            UserCreateDto userCreateDto = new UserCreateDto.Builder().name(name)
                                                                     .age(age)
                                                                     .hobby(hobby)
                                                                     .build();

            @Test
            void UserRepository의_save함수를_호출한다() {

                userDefaultService.create(userCreateDto);

                verify(userRepository, atLeastOnce()).save(any());
            }
        }
    }

    @Nested
    class id를_이용한_유저_조회_함수는 {

        @Nested
        class id값으로_음수가_들어온다면 {

            @ParameterizedTest(name = "{displayName} - id:{0}")
            @ValueSource(longs = {Long.MIN_VALUE, -1, 0})
            void 예외를_발생_시킨다(long id) {

                Throwable throwable = catchThrowable(() -> userDefaultService.findById(id));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(IllegalArgumentException.class);

            }

        }

        @Nested
        class 현재_할당되지_않은_id값이_들어온다면 {

            @ParameterizedTest(name = "{displayName} - id:{0}")
            @ValueSource(longs = {10, Long.MAX_VALUE})
            void 예외를_발생_시킨다(long id) {

                Throwable throwable = catchThrowable(() -> userDefaultService.findById(id));

                assertThat(throwable).isNotNull()
                                     .isInstanceOf(NotFoundException.class);

            }

        }

        @Nested
        class 정상적으로_할당된_id값이_들어온다면 {

            @ParameterizedTest(name = "{displayName} - id:{0}")
            @ValueSource(longs = {1, 100, Long.MAX_VALUE})
            void 해당_id를_가진_User를_반환한다(long id) {
                User savedUser = new User.Builder().id(id)
                                                   .name(name)
                                                   .age(age)
                                                   .hobby(hobby)
                                                   .build();

                when(userRepository.findById(id)).thenReturn(Optional.of(savedUser));

                User foundUser = userDefaultService.findById(id);

                assertThat(foundUser).isNotNull();
                assertThat(foundUser.getId()).isEqualTo(id);
            }

        }

    }
}