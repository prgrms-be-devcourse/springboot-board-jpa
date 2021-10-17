package com.example.boardbackend.controller;

import com.example.boardbackend.dto.UserDto;
import com.example.boardbackend.dto.request.LoginRequest;
import com.example.boardbackend.repository.UserRepository;
import com.example.boardbackend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;

    Long userId;
    UserDto userDto = UserDto.builder()
            .email("test@mail.com")
            .password("1234")
            .name("test")
            .age(20)
            .hobby("코딩")
            .build();

    @BeforeEach
    void saveUser() {
        userId = userService.saveUser(userDto).getId();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

//    ------------------------------------------------------------------------------------

    @Test
    @DisplayName("회원가입 요청을 받을 수 있다.")
    void signUp_test() throws Exception {
        // Given
        UserDto newUserDto = UserDto.builder()
                .email("test2@mail.com")
                .password("1234")
                .name("어쩌구")
                .age(25)
                .hobby("개발")
                .build();
        String request = objectMapper.writeValueAsString(newUserDto);

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        );

        // Then
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document(
                                "user/sign-up",
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NULL).description("id"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                        fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                                        fieldWithPath("createdAt").type(JsonFieldType.NULL).description("createdAt")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                        fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt")
                                )
                        )
                );
    }

    @Test
    @DisplayName("로그인 요청을 받을 수 있다.")
    void login_test() throws Exception {
        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
        String request = objectMapper.writeValueAsString(loginRequest);

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        );

        // Then
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document(
                                "user/login",
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("password")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id")
                                )
                        )
                );
    }

    @Test
    @DisplayName("유저 정보 조회 요청을 받을 수 있다.")
    void getUserInfo_test() throws Exception {
        // Given + When
        ResultActions resultActions = mockMvc.perform(
                get("/api/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Then
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document(
                                "user/get",
                                pathParameters(
                                        parameterWithName("id").description("userId")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                        fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt")
                                )
                        )
                );
    }

    @Test
    @DisplayName("유저 삭제 요청을 받을 수 있다.")
    void resign_test() throws Exception {
        // Given + When
        ResultActions resultActions = mockMvc.perform(
                delete("/api/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Then
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document(
                                "user/delete",
                                pathParameters(
                                        parameterWithName("id").description("userId")
                                )
                        )
                );
    }
}