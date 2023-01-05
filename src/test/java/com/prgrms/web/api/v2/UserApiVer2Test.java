package com.prgrms.web.api.v2;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.domain.user.UserRepository;
import com.prgrms.domain.user.UserService;
import com.prgrms.dto.UserDto.LoginRequest;
import com.prgrms.dto.UserDto.Response;
import com.prgrms.dto.UserDto.UserCreateRequest;
import com.prgrms.web.auth.SessionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("sessionCookie 를 적용한 userController 테스트")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class UserApiVer2Test {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionManager sessionManager;

    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String SESSION_NAME = "userId";

    private String toJsonString(final Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    @DisplayName("사용자는 회원가입을 할 수 있다")
    @Test
    void userSignUp() throws Exception {
        UserCreateRequest createSource = new UserCreateRequest(
            "유저1",
            "클라이밍",
            28,
            "testUser1@gmail.com",
            "1q2w3e4r!");

        mockMvc.perform(post("/api/v2/users")
                .content(toJsonString(createSource))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @DisplayName("사용자는 로그인을 할 수 있다")
    @Test
    void login() throws Exception {

        UserCreateRequest createSource = new UserCreateRequest(
            "유저2",
            "클라이밍",
            28,
            "testUser2@gmail.com",
            "1q2w3e4r!");

        userService.insertUser(createSource);

        LoginRequest loginSource = new LoginRequest(
            "testUser2@gmail.com",
            "1q2w3e4r!");

        mockMvc.perform(post("/api/v2/users/login")
                .content(toJsonString(loginSource))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @DisplayName("로그인한 사용자는 마이페이지를 확인 할 수 있다")
    @Test
    void getMyInfo() throws Exception {
        UserCreateRequest createSource = new UserCreateRequest(
            "유저2",
            "클라이밍",
            28,
            "testUser2@gmail.com",
            "1q2w3e4r!");
        LoginRequest loginSource = new LoginRequest(
            "testUser2@gmail.com",
            "1q2w3e4r!");

        userService.insertUser(createSource);
        Response loginUser = userService.login(loginSource);
        sessionManager.checkDuplicateLoginAndRegisterSession(loginUser.getUserId(), request,
            response);

        mockMvc.perform(get("/api/v2/users/me")
            .cookie(response.getCookie(SESSION_NAME)));
    }

    @DisplayName("이메일이 중복되는 경우 상태코드 BAD_REQUEST 를 리턴한다")
    @Test
    void signUpFail() throws Exception {
        UserCreateRequest createSource = new UserCreateRequest(
            "유저1",
            "클라이밍",
            28,
            "testUser1@gmail.com",
            "1q2w3e4r!");

        userService.insertUser(createSource);

        mockMvc.perform(post("/api/v2/users")
                .content(toJsonString(createSource))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("로그인 실패시 상태코드 BAD_REQUEST 를 리턴한다")
    @Test
    void loginFail() throws Exception {

        UserCreateRequest createSource = new UserCreateRequest(
            "유저2",
            "클라이밍",
            28,
            "testUser2@gmail.com",
            "1q2w3e4r!");

        userService.insertUser(createSource);

        LoginRequest loginSource = new LoginRequest(
            "testUser2@gmail.com",
            "1111qqqq@");

        mockMvc.perform(post("/api/v2/users/login")
                .content(toJsonString(loginSource))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

}
