package com.prgrms.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.prgrms.dto.UserDto.UserCreateRequest;
import com.prgrms.dto.UserDto.Response;
import com.prgrms.exception.customException.EmailDuplicateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final String name = "멍뭉이";
    private final String hobby = "공가지고 놀기";
    private final int age = 10;
    private final String password = "dog1234!";

    @DisplayName("입력받은 유저 정보를 저장한다")
    @Test
    void joinUser() {
        //Given
        String email = "dog1234@gmail.com";
        UserCreateRequest request = new UserCreateRequest(name, hobby, age, email, password);
        User user = new User(
            request.name(),
            request.hobby(),
            request.age(),
            request.email(),
            request.password());
        Response expectResponse = new Response(user);
        given(userRepository.save(user)).willReturn(user);

        //When
        Response response = userService.insertUser(request);

        //Then
        verify(userRepository).save(user);
        assertThat(response).isEqualTo(expectResponse);
    }

    @DisplayName("회원가입시 이메일이 중복되었을 경우 EmailDuplicateException을 던진다")
    @Test
    void checkEmailDuplicate() {

        //Given
        String email = "test@naver.com";
        UserCreateRequest request = new UserCreateRequest(name, hobby, age, email, password);

        userService.insertUser(request);
        verify(userRepository).existsByEmail(email);
        given(userRepository.existsByEmail(email)).willThrow(EmailDuplicateException.class);

        //When&Then
        assertThatThrownBy(() -> userService.insertUser(request))
            .isInstanceOf(EmailDuplicateException.class);
    }

}
