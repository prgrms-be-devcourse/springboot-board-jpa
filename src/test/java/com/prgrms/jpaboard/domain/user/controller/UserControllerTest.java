package com.prgrms.jpaboard.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.jpaboard.domain.user.dto.UserRequestDto;
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
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원 생성 테스트 및 문서화")
    void testCreateUserCall() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto("jerry", 25, "누워서 빈둥 거리기");

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("user-create",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("회원 나이").optional(),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("회원 취미").optional()
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("result").type(JsonFieldType.OBJECT).description("응답 결과"),
                                fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("생성된 회원의 ID"),
                                fieldWithPath("result.createdAt").type(JsonFieldType.STRING).description("회원의 생성 시간")
                        )
                ));

    }
}