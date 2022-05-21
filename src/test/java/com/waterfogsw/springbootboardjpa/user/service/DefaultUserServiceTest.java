package com.waterfogsw.springbootboardjpa.user.service;

import com.waterfogsw.springbootboardjpa.common.exception.ResourceNotFoundException;
import com.waterfogsw.springbootboardjpa.user.entity.User;
import com.waterfogsw.springbootboardjpa.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    DefaultUserService defaultUserService;

    @Nested
    @DisplayName("addUser 메서드는")
    class Describe_addUser {

        @Nested
        @DisplayName("인자가 null 이 아닌 경우")
        class Context_with_arg_not_null {

            @Test
            @DisplayName("repository 의 save 메서드를 호출한다")
            void It_throw_exception() {
                final var user = User.builder()
                        .name("test")
                        .email("test@naver.com")
                        .build();

                defaultUserService.addUser(user);

                verify(userRepository).save(any(User.class));
            }
        }

        @Nested
        @DisplayName("인자가 null 인 경우")
        class Context_with_null_arg {

            @Test
            @DisplayName("IllegalArgumentException 예외를 던진다")
            void It_throw_IllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> defaultUserService.addUser(null));
            }
        }

    }

    @Nested
    @DisplayName("getOne 메서드는")
    class Describe_getOne {

        @Nested
        @DisplayName("id 값이 양수이고, 엔티티가 존재하면")
        class Context_with_PositiveId {

            @ParameterizedTest
            @ValueSource(longs = {1, Long.MAX_VALUE})
            @DisplayName("findById 메서드를 호출한다")
            void It_CallFindById(long src) {
                final var user = User.builder()
                        .name("test")
                        .email("test@naver.com")
                        .build();

                when(userRepository.findById(any())).thenReturn(Optional.of(user));

                defaultUserService.getOne(src);
                verify(userRepository).findById(anyLong());
            }
        }

        @Nested
        @DisplayName("id 값이 양수이고, 해당 엔티티가 존재하지 않으면")
        class Context_with_NotExist {

            @ParameterizedTest
            @ValueSource(longs = {1, Long.MAX_VALUE})
            @DisplayName("ResourceNotFoundException 이 발생한다")
            void It_ThrowsResourceNotFoundException(long src) {
                when(userRepository.findById(any())).thenReturn(Optional.empty());

                assertThrows(ResourceNotFoundException.class, () -> defaultUserService.getOne(src));
            }
        }

        @Nested
        @DisplayName("id 값이 양수가 아니면")
        class Context_with_NotPositiveId {

            @ParameterizedTest
            @ValueSource(longs = {0, -1, Long.MIN_VALUE})
            @DisplayName("IllegalArgumentException 이 발생한다")
            void It_ThrowsIllegalArgumentException(long src) {
                assertThrows(IllegalArgumentException.class, () -> defaultUserService.getOne(src));
            }
        }
    }
}
