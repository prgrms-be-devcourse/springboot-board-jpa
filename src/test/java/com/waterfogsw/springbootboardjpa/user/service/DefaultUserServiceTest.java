package com.waterfogsw.springbootboardjpa.user.service;

import com.waterfogsw.springbootboardjpa.user.entity.User;
import com.waterfogsw.springbootboardjpa.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

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
}
