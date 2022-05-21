package com.kdt.prgrms.board.user;

import com.kdt.prgrms.board.entity.user.User;
import com.kdt.prgrms.board.entity.user.UserRepository;
import com.kdt.prgrms.board.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Nested
    @DisplayName("addUser 메서드는")
    class DescribeAddUser {

        @Nested
        @DisplayName("인자로 null을 받으면")
        class ContextReceiveNull {

            User user = null;

            @Test
            @DisplayName("잘못된 인자 예외를 던진다.")
            void itThrowIllegalArgumentException() {

                Assertions.assertThatThrownBy(() -> userService.addUser(user))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("유저 객체가 null이 아니면")
        class ContextReceiveUser {

            User user = User.builder()
                    .name("hello")
                    .age(14)
                    .build();

            @Test
            @DisplayName("Repository의 save메서드를 호출한다..")
            void itCallRepositorySave() {

                userService.addUser(user);

                verify(userRepository).save(user);
            }
        }
    }
}
