package com.prgrms.java.service;

import com.prgrms.java.domain.Email;
import com.prgrms.java.domain.LoginState;
import com.prgrms.java.dto.user.*;
import com.prgrms.java.exception.AuthenticationFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class UserServiceTest {

    private FakeUserRepository userRepository = new FakeUserRepository();
    private UserService userService = new UserService(userRepository);

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @DisplayName("유저를 등록할 수 있다.")
    @Test
    void joinUser() {
        // given
        PostUserRequest postUserRequest = new PostUserRequest(new UserLoginInfo("example@gmail.com", "1234"), new UserSideInfo("택승", 25, "MOVIE"));

        // when
        userService.joinUser(postUserRequest);

        // then
        Long count = userRepository.count();
        assertThat(count).isEqualTo(1L);
    }

    @DisplayName("유저는 로그인을 할 수 있다.")
    @Test
    void loginUser() {
        // given
        PostUserRequest postUserRequest = new PostUserRequest(new UserLoginInfo("example@gmail.com", "1234"), new UserSideInfo("택승", 25, "MOVIE"));
        userService.joinUser(postUserRequest);

        PostLoginRequest postLoginRequest = new PostLoginRequest("example@gmail.com", "1234");

        // when
        LoginState actual = userService.loginUser(postLoginRequest);

        // then
        assertThat(actual)
                .isEqualTo(LoginState.SUCCESS);
    }

    @DisplayName("유저는 개인정보를 조회할 수 있다.")
    @Test
    void getUserDetails() {
        // given
        PostUserRequest postUserRequest = new PostUserRequest(new UserLoginInfo("example@gmail.com", "1234"), new UserSideInfo("택승", 25, "MOVIE"));
        userService.joinUser(postUserRequest);

        Email email = new Email("example@gmail.com");

        GetUserDetailsResponse expect = new GetUserDetailsResponse(postUserRequest.userSideInfo().name(), postUserRequest.userLoginInfo().email(), postUserRequest.userSideInfo().age(), postUserRequest.userSideInfo().hobby());

        // when
        GetUserDetailsResponse actual = userService.getUserDetails(email);

        // then
        assertThat(actual)
                .isEqualTo(expect);
    }

    @DisplayName("유저가 회원이 아닌지 확인할 수 있다.")
    @Test
    void validMember() {
        // given
        Email email = new Email("example@gmail.com");

        // when then
        assertThrows(AuthenticationFailedException.class, () -> userService.validMember(email));
    }
}