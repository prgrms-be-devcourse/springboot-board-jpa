package com.example.boardbackend.controller;

import com.example.boardbackend.dto.UserDto;
import com.example.boardbackend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    ObjectMapper objectMapper;

    UserDto userDto = UserDto.builder()
            .email("test@mail.com")
            .password("1234")
            .name("test")
            .age(20)
            .hobby("코딩")
            .build();

    @BeforeEach
    void setUp(){
        userService.saveUser(userDto);
    }

    @AfterEach
    void tearDown(){
        userService.deleteUserAll();
    }

//    ------------------------------------------------------------------------------------

    @Test
    void signUp_test() throws Exception {
        // Given
        UserDto newUserDto = UserDto.builder()
                .email("test2@mail.com")
                .password("1234")
                .name("test2")
                .age(25)
                .hobby("개발")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserDto)));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document(
                                "user/sign-up",

                        )

                );
    }

    @Test
    void login_test() {
    }

    @Test
    void getUserInfo_test() {
    }

    @Test
    void resign_test() {
    }
}