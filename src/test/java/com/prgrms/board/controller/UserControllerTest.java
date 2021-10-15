package com.prgrms.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.board.domain.User;
import com.prgrms.board.dto.IdResponse;
import com.prgrms.board.dto.user.UserCreateRequest;
import com.prgrms.board.dto.user.UserFindRequest;
import com.prgrms.board.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("dahee")
                .age(99)
                .hobby("노래듣기")
                .build();
    }

    @DisplayName("사용자 정보 저장")
    @Test
    void createUserTest() throws Exception {
        given(userService.createUser(any(UserCreateRequest.class))).willReturn(new IdResponse(user.getId()));

        String body = objectMapper.writeValueAsString(new UserCreateRequest("dahee", 90, "노래듣기"));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "create-user",
                                requestFields(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                        fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id")
                                )
                        )
                );
    }

    @DisplayName("특정 사용자 정보 조회")
    @Test
    void findUserTest() throws Exception {
        given(userService.findUser(anyLong())).willReturn(new UserFindRequest(user));

        RequestBuilder request = RestDocumentationRequestBuilders
                .get("/users/{id}", user.getId())
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(
                        document("find-user",
                                pathParameters(
                                        parameterWithName("id").description("id")
                                ),
                                responseFields(

                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                        fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                                        fieldWithPath("createdAt").type(JsonFieldType.NULL).description("createdAt"),
                                        fieldWithPath("updatedAt").type(JsonFieldType.NULL).description("updatedAt")
                                )
                        )
                );
    }

    @DisplayName("사용자 정보 수정")
    @Test
    void modifyUserTest() throws Exception {
        given(userService.modifyUser(anyLong(), any(UserCreateRequest.class))).willReturn(new IdResponse(user.getId()));

        String body = objectMapper.writeValueAsString(new UserCreateRequest("dahee", 90, "노래듣기"));

        RequestBuilder request = RestDocumentationRequestBuilders
                .put("/users/{id}", user.getId())
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "modify-user",
                                pathParameters(
                                        parameterWithName("id").description("id")
                                ),
                                requestFields(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                        fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id")
                                )
                        )
                );
    }

    @DisplayName("사용자 정보 삭제")
    @Test
    void removeUserTest() throws Exception {
        given(userService.removeUser(anyLong())).willReturn(new IdResponse(user.getId()));

        RequestBuilder request = RestDocumentationRequestBuilders
                .delete("/users/{id}", user.getId());

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "remove-user",
                                pathParameters(
                                        parameterWithName("id").description("id")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id")
                                )
                        )
                );
    }

}