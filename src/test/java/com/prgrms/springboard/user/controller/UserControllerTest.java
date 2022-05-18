package com.prgrms.springboard.user.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.springboard.global.common.ApiResponse;
import com.prgrms.springboard.user.dto.CreateUserRequest;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Sql("classpath:db/init.sql")
class UserControllerTest {

    private static final CreateUserRequest CREATE_USER_REQUEST = new CreateUserRequest("유민환", 26, "낚시");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("회원을 생성한다.")
    @Test
    void createUser() throws Exception {
        // given
        // when
        ResultActions resultActions = createUserResponse(CREATE_USER_REQUEST);

        // then
        resultActions.andExpect(status().isCreated())
            .andExpect(jsonPath("data").exists())
            .andDo(print())
            .andDo(document("user-save",
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER).description("생성된 회원 번호"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                )
            ));
    }

    @DisplayName("ID를 통해 회원을 조회한다.")
    @Test
    void findOne() throws Exception {
        // given
        ResultActions userResponse = createUserResponse(CREATE_USER_REQUEST);
        Long id = getSavedId(userResponse);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/users/{id}", id)
            .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(document("user-findOne",
                pathParameters(
                    parameterWithName("id").description("회원 번호")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("회원"),
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회원 번호"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                    fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("회원 나이"),
                    fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("회원 취미"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                )
            ));
    }

    @DisplayName("존재하지 않는 회원 조회 시 NOT_FOUND 응답코드를 반환한다. ")
    @Test
    void findOne_NotFound() throws Exception {
        // given
        Long id = 2L;

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/users/{id}", id)
            .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isNotFound())
            .andExpect(jsonPath("statusCode", HttpStatus.NOT_FOUND.value()).exists())
            .andExpect(jsonPath("data", "ID가 2인 회원은 없습니다.").exists())
            .andExpect(jsonPath("serverDateTime").exists())
            .andDo(print())
            .andDo(document("user-notFound",
                pathParameters(
                    parameterWithName("id").description("회원 번호")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.STRING).description("에러 메시지"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                )
            ));
    }

    private Long getSavedId(ResultActions userResponse) throws JsonProcessingException, UnsupportedEncodingException {
        return Long.parseLong(objectMapper.readValue(userResponse.andReturn().getResponse().getContentAsString(),
            ApiResponse.class).getData().toString());
    }

    private ResultActions createUserResponse(CreateUserRequest userRequest) throws Exception {
        return mockMvc.perform(post("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userRequest)));
    }

}
