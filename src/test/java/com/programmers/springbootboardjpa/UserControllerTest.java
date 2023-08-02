package com.programmers.springbootboardjpa;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbootboardjpa.domain.user.Hobby;
import com.programmers.springbootboardjpa.dto.user.UserCreateRequest;
import com.programmers.springbootboardjpa.dto.user.UserUpdateRequest;
import com.programmers.springbootboardjpa.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    void saveUser() {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .name("changhyeon")
                .age(28)
                .hobby(Hobby.TRAVEL)
                .build();

        userService.save(userCreateRequest);
    }

    @Test
    @Order(1)
    @DisplayName("유저를 저장할 수 있다.")
    void createUser() throws Exception {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .name("changhyeon")
                .age(28)
                .hobby(Hobby.GAME)
                .build();

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("user-save",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                        )
                ));
    }

    @Test
    @Order(2)
    @DisplayName("유저를 유저 ID로 조회할 수 있다.")
    void getUserById() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-findById",
                        pathParameters(
                                parameterWithName("id").description("유저 아이디")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("유저 취미"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 시각"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답 시간")
                        )
                ));
    }

    @Test
    @Order(3)
    @DisplayName("유저를 10명씩 불러올 수 있다.")
    void getAllUser() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/users")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-findAll",
                        queryParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("조회 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("데이터"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("data[].age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("data[].hobby").type(JsonFieldType.STRING).description("유저 취미"),
                                fieldWithPath("data[].createdBy").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("생성 시각"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답 시간")
                        )
                ));

    }

    @Test
    @Order(4)
    @DisplayName("유저의 이름과 취미를 수정할 수 있다.")
    void updateUserById() throws Exception {
        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .name("hwangchanghyeon")
                .hobby(Hobby.GAME)
                .build();

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/users/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-updateById",
                        pathParameters(
                                parameterWithName("id").description("유저 아이디")
                        ),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("유저 취미")
                        )
                ));
    }

    @Test
    @Order(5)
    @DisplayName("유저를 유저의 ID로 삭제할 수 있다.")
    void deleteUserById() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("user-deleteById",
                        pathParameters(
                                parameterWithName("id").description("유저 아이디")
                        )
                ));
    }

}
