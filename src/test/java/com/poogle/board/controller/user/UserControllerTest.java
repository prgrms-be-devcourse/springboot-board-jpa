package com.poogle.board.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poogle.board.model.user.User;
import com.poogle.board.service.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;

    @BeforeAll
    void setup() {
        UserRequest userRequest = UserRequest.builder()
                .name("poogle")
                .age(27)
                .hobby("listening to music")
                .build();

        User user = userService.join(userRequest.newUser());
    }

    @Test
    @DisplayName("사용자 추가 테스트")
    void save_success_test() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .name("test")
                .age(27)
                .hobby("watching movie")
                .build();
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-save",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("request user name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("request user age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("request user hobby")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("success value: boolean"),
                                fieldWithPath("response").type(JsonFieldType.OBJECT).description("response"),
                                fieldWithPath("response.id").type(JsonFieldType.NUMBER).description("response user id"),
                                fieldWithPath("response.name").type(JsonFieldType.STRING).description("response user name"),
                                fieldWithPath("response.age").type(JsonFieldType.NUMBER).description("response user age"),
                                fieldWithPath("response.hobby").type(JsonFieldType.STRING).description("response user hobby"),
                                fieldWithPath("response.createdAt").type(JsonFieldType.STRING).description("response user createdAt"),
                                fieldWithPath("response.modifiedAt").type(JsonFieldType.STRING).description("response user modifiedAt"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("error")
                        )
                ));
    }

    @Test
    @DisplayName("사용자 수정 테스트")
    void edit_success_test() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .name("new poogle")
                .age(37)
                .hobby("reading book")
                .build();
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-edit",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("request user name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("request user age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("request user hobby")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("success value: boolean"),
                                fieldWithPath("response").type(JsonFieldType.OBJECT).description("response"),
                                fieldWithPath("response.id").type(JsonFieldType.NUMBER).description("response user id"),
                                fieldWithPath("response.name").type(JsonFieldType.STRING).description("response user name"),
                                fieldWithPath("response.age").type(JsonFieldType.NUMBER).description("response user age"),
                                fieldWithPath("response.hobby").type(JsonFieldType.STRING).description("response user hobby"),
                                fieldWithPath("response.createdAt").type(JsonFieldType.STRING).description("response user createdAt"),
                                fieldWithPath("response.modifiedAt").type(JsonFieldType.STRING).description("response user modifiedAt"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("error")
                        )
                ));
    }

    @Test
    @DisplayName("사용자 단건 조회 테스트")
    void get_one_user() throws Exception {
        mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-get",
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("success value: boolean"),
                                fieldWithPath("response").type(JsonFieldType.OBJECT).description("response"),
                                fieldWithPath("response.id").type(JsonFieldType.NUMBER).description("response user id"),
                                fieldWithPath("response.name").type(JsonFieldType.STRING).description("response user name"),
                                fieldWithPath("response.age").type(JsonFieldType.NUMBER).description("response user age"),
                                fieldWithPath("response.hobby").type(JsonFieldType.STRING).description("response user hobby"),
                                fieldWithPath("response.createdAt").type(JsonFieldType.STRING).description("response user createdAt"),
                                fieldWithPath("response.modifiedAt").type(JsonFieldType.STRING).description("response user modifiedAt"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("error")
                        )
                ));
    }

    @Test
    @DisplayName("사용자 전체 조회 테스트")
    void get_all_users() throws Exception {
        mockMvc.perform(get("/api/users")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
