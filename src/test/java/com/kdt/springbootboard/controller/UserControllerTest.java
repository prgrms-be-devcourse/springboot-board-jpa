package com.kdt.springbootboard.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kdt.springbootboard.common.RestDocsConfiguration;
import com.kdt.springbootboard.dto.user.UserCreateRequest;
import com.kdt.springbootboard.dto.user.UserResponse;
import com.kdt.springbootboard.dto.user.UserUpdateRequest;
import com.kdt.springbootboard.repository.UserRepository;
import com.kdt.springbootboard.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@SpringBootTest
class UserControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;

    private Long id;

    @BeforeEach
    void setUp() throws Exception {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
            .name("전찬의")
            .email("jcu011@naver.com")
            .age("29")
            .hobby("watching Netflix")
            .build();

        // when
        id = userService.insert(request);
        UserResponse response = userService.findById(id);

        // then
        assertThat(response.getName()).isEqualTo(request.getName());
        assertThat(response.getEmail()).isEqualTo(request.getEmail());
        assertThat(response.getAge()).isEqualTo(request.getAge());
        assertThat(response.getHobby()).isEqualTo(request.getHobby());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유저등록")
    public void insertUserTest() throws Exception {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
            .name("전찬영")
            .email("jcy9476@naver.com")
            .age("28")
            .hobby("eating_something")
            .build();

        // when // then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("UserController/insertUser",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                    fieldWithPath("age").type(JsonFieldType.STRING).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("serverDatetime"),
                    fieldWithPath("data").description(JsonFieldType.NUMBER).description("data")
                )
            ));
    }

    @Test
    @DisplayName("유저조회")
    public void getUserTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("UserController/getUser",
                pathParameters(
                    parameterWithName("id").description(JsonFieldType.NUMBER).description("userId")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("serverDatetime"),
                    fieldWithPath("data.id").description(JsonFieldType.NUMBER).description("data.id"),
                    fieldWithPath("data.name").description(JsonFieldType.STRING).description("data.name"),
                    fieldWithPath("data.email").description(JsonFieldType.NUMBER).description("data.email"),
                    fieldWithPath("data.age").description(JsonFieldType.STRING).description("data.age"),
                    fieldWithPath("data.hobby").description(JsonFieldType.STRING).description("data.hobby")
                )
            ));
    }

    @Test
    @DisplayName("모든_유저_페이징_조회")
    public void getAllUserTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/users")
                .param("page", String.valueOf(0)) // 페이지
                .param("size", String.valueOf(10)) // 사이즈
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
        // Todo : 페이징 조회 문서화
            /*.andDo(document("UserController/getAllUser",
                requestParameters(
                    parameterWithName("page").description(JsonFieldType.NUMBER).description("Page number of post pagination."),
                    parameterWithName("size").description(JsonFieldType.NUMBER).description("Page size of post pagination.")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("serverDatetime"),
                    fieldWithPath("data.[]").description(JsonFieldType.NUMBER).description("data"),
                    fieldWithPath("data.[].id").description(JsonFieldType.NUMBER).description("data.id"),
                    fieldWithPath("data.[].name").description(JsonFieldType.STRING).description("data.name"),
                    fieldWithPath("data.[].email").description(JsonFieldType.NUMBER).description("data.email"),
                    fieldWithPath("data.[].age").description(JsonFieldType.STRING).description("data.age"),
                    fieldWithPath("data.[].hobby").description(JsonFieldType.STRING).description("data.hobby")
                )
            ));*/
    }

    @Test
    @DisplayName("유저수정")
    public void updateUserTest() throws Exception {
        // given
        UserUpdateRequest request = UserUpdateRequest.builder()
            .id(id)
            .name("전찬의")
            .email("jcu011@naver.com")
            .age("29")
            .hobby("playing game")
            .build();

        // when // then
        mockMvc.perform(put("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("UserController/updateUser",
                requestFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                    fieldWithPath("age").type(JsonFieldType.STRING).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("serverDatetime"),
                    fieldWithPath("data.id").description(JsonFieldType.NUMBER).description("data.id"),
                    fieldWithPath("data.name").description(JsonFieldType.STRING).description("data.name"),
                    fieldWithPath("data.email").description(JsonFieldType.NUMBER).description("data.email"),
                    fieldWithPath("data.age").description(JsonFieldType.STRING).description("data.age"),
                    fieldWithPath("data.hobby").description(JsonFieldType.STRING).description("data.hobby")
                )
            ));
    }

    @Test
    @DisplayName("유저삭제")
    public void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("UserController/deleteUser",
                pathParameters(
                    parameterWithName("id").description(JsonFieldType.NUMBER).description("userId")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("serverDatetime"),
                    fieldWithPath("data").description(JsonFieldType.STRING).description("data")
                )
            ));
    }

}