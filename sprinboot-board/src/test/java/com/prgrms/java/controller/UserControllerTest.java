package com.prgrms.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.java.domain.HobbyType;
import com.prgrms.java.domain.User;
import com.prgrms.java.dto.user.LoginRequest;
import com.prgrms.java.dto.user.CreateUserRequest;
import com.prgrms.java.dto.user.UserLoginInfo;
import com.prgrms.java.dto.user.UserSideInfo;
import com.prgrms.java.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("유저는 회원가입을 할 수 있다.")
    @Test
    void joinUser() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest(new UserLoginInfo("example@gmail.com", "1234"), new UserSideInfo("택승", 25, HobbyType.MOVIE));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(
                        document("user-join",
                                requestFields(
                                        fieldWithPath("userLoginInfo").type(JsonFieldType.OBJECT).description("로그인 정보"),
                                        fieldWithPath("userLoginInfo.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("userLoginInfo.password").type(JsonFieldType.STRING).description("비밀번호"),
                                        fieldWithPath("userSideInfo").type(JsonFieldType.OBJECT).description("개인 정보"),
                                        fieldWithPath("userSideInfo.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("userSideInfo.age").type(JsonFieldType.NUMBER).description("나이"),
                                        fieldWithPath("userSideInfo.hobby").type(JsonFieldType.STRING).description("취미")
                                ))
                );

    }

    @DisplayName("유저는 로그인을 할 수 있다.")
    @Test
    void loginUser() throws Exception {
        User user = new User("이택승", "example@gmail.com", "1234", 25, HobbyType.MOVIE);
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest("example@gmail.com", "1234");
        String expectCookie = new StringBuilder().append("login-token=").append(loginRequest.email()).toString();

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.SET_COOKIE, expectCookie))
                .andDo(print())
                .andDo(
                        document("user-login",
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                ),
                                responseHeaders(
                                        headerWithName(HttpHeaders.SET_COOKIE).description("인증을 위한 쿠키")
                                )
                        )
                );
    }

    @DisplayName("유저는 개인정보를 조회할 수 있다.")
    @Test
    void getUserDetails() throws Exception {
        User user = new User("이택승", "example@gmail.com", "1234", 25, HobbyType.MOVIE);
        userRepository.save(user);

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(getCookie()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("user-getDetails",
                                responseFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                        fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                                )
                        )
                );
    }

    private Cookie getCookie() {
        return new Cookie("login-token", "example@gmail.com");
    }
}