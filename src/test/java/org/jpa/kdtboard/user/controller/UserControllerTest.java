package org.jpa.kdtboard.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jpa.kdtboard.domain.board.UserRepository;
import org.jpa.kdtboard.user.dto.UserDto;
import org.jpa.kdtboard.user.service.UserService;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by yunyun on 2021/10/14.
 */

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 계정을 저장할 수 있다.")
    void saveTest() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UserDto.builder()
                        .createdBy("관리자")
                        .age(11)
                        .name("홍길동")
                        .hobby("잠")
                        .build())))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("users-save",
                        requestFields(
                                fieldWithPath("createdBy").type(JsonFieldType.STRING).description("사용자 계정이 만든 대상자"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("사용자 나이"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("사용자 취미")
                                ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("요청의 의해 Api에서 전달할 데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("Api에서 응답한 시각")
                        )
                    )
                );


    }
}