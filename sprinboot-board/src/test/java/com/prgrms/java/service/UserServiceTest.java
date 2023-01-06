package com.prgrms.java.service;

import com.prgrms.java.domain.Email;
import com.prgrms.java.domain.HobbyType;
import com.prgrms.java.dto.user.*;
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
        CreateUserRequest createUserRequest = new CreateUserRequest(new UserLoginInfo("example@gmail.com", "1234"), new UserSideInfo("택승", 25, HobbyType.MOVIE));

        // when
        userService.joinUser(createUserRequest);

        // then
        Long count = userRepository.count();
        assertThat(count).isEqualTo(1L);
    }

    @DisplayName("유저는 로그인을 할 수 있다.")
    @Test
    void loginUser() {
        // given
        CreateUserRequest createUserRequest = new CreateUserRequest(new UserLoginInfo("example@gmail.com", "1234"), new UserSideInfo("택승", 25, HobbyType.MOVIE));
        Long expect = userService.joinUser(createUserRequest);

        LoginRequest loginRequest = new LoginRequest("example@gmail.com", "1234");

        // when
        Long actual = userService.loginUser(loginRequest);

        // then
        assertThat(actual)
                .isEqualTo(expect);
    }

    @DisplayName("유저는 개인정보를 조회할 수 있다.")
    @Test
    void getUserDetails() {
        // given
        CreateUserRequest createUserRequest = new CreateUserRequest(new UserLoginInfo("example@gmail.com", "1234"), new UserSideInfo("택승", 25, HobbyType.MOVIE));
        userService.joinUser(createUserRequest);

        Email email = new Email("example@gmail.com");

        GetUserDetailsResponse expect = new GetUserDetailsResponse(createUserRequest.userSideInfo().name(), createUserRequest.userLoginInfo().email(), createUserRequest.userSideInfo().age(), createUserRequest.userSideInfo().hobby());

        // when
        GetUserDetailsResponse actual = userService.getUserDetails(email);

        // then
        assertThat(actual)
                .isEqualTo(expect);
    }
}