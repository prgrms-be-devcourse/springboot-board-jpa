package com.ys.board.domain.user.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ys.board.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @DisplayName("유저 생성 - post /api/v1/users - 유저 생성에 성공한다.")
    @Test
    void createUserSuccess() throws Exception {
        //given
        String name = "ys";
        int age = 28;
        UserCreateRequest userCreateRequest = new UserCreateRequest(name, age, "");

        this.mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateRequest))
            ).andExpect(status().isCreated())
            .andDo(document("users-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),

                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미").optional()
                ),
                responseFields(
                    fieldWithPath("userId").description("생성된 userId")
                ))
            )
            .andDo(print());
    }

    @DisplayName("유저 생성 실패- post /api/v1/users - 유저 생성에 실패한다.- 409")
    @Test
    void createUserFail409Conflict() throws Exception {
        //given
        String name = "ys";
        int age = 28;
        UserCreateRequest userCreateRequest = new UserCreateRequest(name, age, "");

        userService.createUser(userCreateRequest);

        this.mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateRequest))
            )
            .andExpect(status().isConflict())
            .andDo(document("users-create-fail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),

                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미").optional()
                ),
                responseFields(
                    fieldWithPath("timeStamp").description("서버 응답 시간"),
                    fieldWithPath("message").description("예외 메시지"),
                    fieldWithPath("requestUrl").description("요청한 url")
                ))
            )
            .andDo(print());
    }

}