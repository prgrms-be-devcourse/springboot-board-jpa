package com.example.jpaboard.service.user;

import com.example.jpaboard.domain.user.User;
import com.example.jpaboard.domain.user.UserRepository;
import com.example.jpaboard.exception.CustomException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.Field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private static final Long id = 1L;
    private static final User user = new User("이름", 10, "운동");

    @BeforeAll
    public static void setup() {
        Field userId;

        try {
            userId = user.getClass().getDeclaredField("id");
            userId.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            userId.set(user, id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Nested
    class save메서드는 {
        @Test
        @DisplayName("유저를 저장하고 유저 아이디를 반환한다")
        void 유저를_저장하고_유저_아이디를_반환한다() {
            //given
            given(userRepository.save(any(User.class)))
                                .willReturn(user);

            //when
            Long userId = userService.save("이름", 10, "운동");

            //then
            verify(userRepository).save(any(User.class));
            assertThat(userId).isEqualTo(id);
        }

        @Nested
        class 이름이_null또는_공백이거나_빈_값이라면 {
            @DisplayName("BadRequest 응답을 보낸다")
            @ParameterizedTest
            @NullAndEmptySource
            @ValueSource(strings = {" "})
            void BadRequest_응답을_보낸다(String name) {
                //given
                //when, then
                assertThatThrownBy(() ->
                        userService.save(name, 10, "운동"))
                                   .isInstanceOf(CustomException.class)
                                   .hasMessage("필수 입력 값이 없습니다.");
            }
        }
    }
}