package com.programmers.springbootboardjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbootboardjpa.dto.user.request.UserCreationRequest;
import com.programmers.springbootboardjpa.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 생성 테스트")
    void createUserTest() throws Exception {
        UserCreationRequest request = new UserCreationRequest("UserName", 33L, "UserHobby");

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-create",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("회원 나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("회원 취미")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("생성된 회원 데이터의 id"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));

    }
}