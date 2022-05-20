package com.prgrms.board.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.board.user.dto.UserRequest;
import com.prgrms.board.user.dto.UserResponse;
import com.prgrms.board.user.repository.UserRepository;
import com.prgrms.board.user.service.UserService;
import org.assertj.core.api.Assertions;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.param;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    UserResponse userResponse;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        UserRequest userRequest = new UserRequest(
                "dhkstn",
                27L,
                "soccer"
        );
        userResponse = userService.insert(userRequest);
        Assertions.assertThat(userResponse.getUserId()).isNotNull();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void user_저장() throws Exception {
        UserRequest userRequest = new UserRequest(
                "wansu",
                27L,
                "soccer"
        );

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-save",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDateTime")
                        )
                ));
    }

    @Test
    void user_조회() throws Exception {
        mockMvc.perform(get("/api/user/{userId}", userResponse.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-getOne",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDateTime")
                        )));
    }

    @Test
    void userALL_조회() throws Exception {
        mockMvc.perform(get("/api/user",
                        param("page", "0"),
                        param("size", "3"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-getAll",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("content"),
                                fieldWithPath("data.content[].userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("data.content[].name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("data.content[].age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("data.content[].hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("pageable"),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("sort"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsorted"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("offset"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageNumber"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("pageSize"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("paged"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("unpaged"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("first"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("last"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("size"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("number"),
                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("sort"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsorted"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDateTime")
                        )
                ));
    }

    @Test
    void user_삭제() throws Exception {
        mockMvc.perform(delete("/api/user/{userId}", userResponse.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-delete",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("data"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDateTime")
                        )));
    }
}