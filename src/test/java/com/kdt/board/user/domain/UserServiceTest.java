package com.kdt.board.user.domain;

import com.kdt.board.user.application.UserServiceImpl;
import com.kdt.board.user.application.dto.request.UserRegistrationRequestDto;
import com.kdt.board.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Nested
    @DisplayName("register 메서드는")
    class Describe_register {

        @Nested
        @DisplayName("전달인자가 null이면")
        class Context_with_null_argument {

            @Test
            @DisplayName("IllegalArgumentException이 발생한다.")
            void It_response_() {
                //given
                final UserRegistrationRequestDto userRegistrationRequestDto = null;

                //when, then
                assertThrows(IllegalArgumentException.class, () -> userService.register(userRegistrationRequestDto));
            }
        }

        @Nested
        @DisplayName("전달인자가 null이 아닌 정상적인 값이면")
        class Context_with_ {

            @Test
            @DisplayName("repository save 메소드를 호출한다.")
            void It_response_() {
                //given
                final UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto.Builder()
                        .name("test")
                        .email("test@test.com")
                        .build();

                //when
                userService.register(userRegistrationRequestDto);

                //then
                verify(userRepository, times(1)).save(any(User.class));
            }
        }
    }
}
