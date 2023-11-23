package com.example.board.controller;

import com.example.board.converter.UserConverter;
import com.example.board.domain.User;
import com.example.board.dto.request.user.CreateUserRequest;
import com.example.board.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class UserControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Autowired
    UserControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void 유저를_생성한다() throws Exception {
        // given
        CreateUserRequest requestDto = CreateUserRequest.builder()
                .name("빙봉씨")
                .age(30)
                .hobby("러닝")
                .build();

        // when & then
        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andDo(document("user/create",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("hobby").optional().type(JsonFieldType.STRING).description("유저 취미")
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("생성된 유저의 ID"),
                                fieldWithPath("datetime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));
    }

    @Test
    void 유저를_조회한다() throws Exception {
        // given
        CreateUserRequest requestDto = CreateUserRequest.builder()
                .name("삥뽕씨")
                .age(30)
                .hobby("러닝")
                .build();

        User user = userRepository.save(UserConverter.toUser(requestDto));

        // when & then
        mockMvc.perform(get("/v1/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andDo(document("user/get",
                        pathParameters(
                                parameterWithName("id").description("유저 ID")
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("유저 ID"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("data.hobby").optional().type(JsonFieldType.STRING).description("유저 취미"),
                                fieldWithPath("datetime").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("유저 생성 시간"),
                                fieldWithPath("data.updatedAt").type(JsonFieldType.STRING).description("유저 수정 시간")
                        )
                ));
    }

}
