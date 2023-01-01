package com.prgrms.springbootboardjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.springbootboardjpa.dto.UserRequest;
import com.prgrms.springbootboardjpa.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private UserService userService;

    private long id;

    @BeforeEach
    void setup() {
        UserRequest userRequest = UserRequest.builder()
                .name("김창규")
                .age(26)
                .hobby("축구")
                .build();

        id = userService.save(userRequest);
    }

    @Test
    void saveTest() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .name("김창규")
                .age(26)
                .hobby("축구")
                .build();

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("users-save",
                        requestFields(
                                fieldWithPath("name").type(STRING).description("이름"),
                                fieldWithPath("age").type(NUMBER).description("나이"),
                                fieldWithPath("hobby").type(STRING).description("취미")
                        ),
                        responseFields(
                                fieldWithPath("data").type(NUMBER).description("등록된 사용자 식별값"),
                                fieldWithPath("serverDatetime").type(STRING).description("서버 시간")
                        )));
    }

    @Test
    void getOneTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("users-get",
                        pathParameters(
                                parameterWithName("id").description("사용자 식별값")
                        ),
                        responseFields(
                                fieldWithPath("data").type(OBJECT).description("사용자 데이터"),
                                fieldWithPath("data.id").type(NUMBER).description("사용자 식별값"),
                                fieldWithPath("data.name").type(STRING).description("사용자 이름"),
                                fieldWithPath("data.age").type(NUMBER).description("사용자 나이"),
                                fieldWithPath("data.hobby").type(STRING).description("사용자 취미"),
                                fieldWithPath("data.createdBy").type(STRING).description("사용자 생성자 (이름과 동일)"),
                                fieldWithPath("data.createdAt").type(STRING).description("생성일"),
                                fieldWithPath("data.updatedAt").type(STRING).description("수정일"),
                                fieldWithPath("serverDatetime").type(STRING).description("서버 시간")
                        )));
    }

    @Test
    void getAllTest() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updateTest() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .name("김창규")
                .age(25)
                .hobby("게임")
                .build();

        mockMvc.perform(post("/api/v1/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}