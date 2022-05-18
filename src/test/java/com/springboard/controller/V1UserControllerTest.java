package com.springboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboard.user.dto.*;
import com.springboard.user.repository.UserRepository;
import com.springboard.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("User E2E 테스트")
class V1UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    UserResponse response;

    @BeforeEach
    void setUp() {
        UserRequest request = new UserRequest("moosong", 25, "viola");
        response = userService.save(request);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("User 목록 조회 테스트")
    void getAll() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
            .andExpect(jsonPath("$.content[0].id").value(response.id()))
            .andDo(document("user-getAll",
                responseFields(
                    fieldWithPath("content[]").type(JsonFieldType.ARRAY).description("content[]"),
                    fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("content[].id"),
                    fieldWithPath("content[].name").type(JsonFieldType.STRING).description("content[].name"),
                    fieldWithPath("content[].age").type(JsonFieldType.NUMBER).description("content[].age"),
                    fieldWithPath("content[].hobby").type(JsonFieldType.STRING).description("content[].hobby"),
                    fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("content[].createdAt"),
                    fieldWithPath("content[].updatedAt").type(JsonFieldType.STRING).description("content[].updatedAt"),
                    fieldWithPath("pageable").type(JsonFieldType.OBJECT).description("pageable"),
                    fieldWithPath("pageable.sort").type(JsonFieldType.OBJECT).description("pageable.sort"),
                    fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("pageable.sort.empty"),
                    fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.sorted"),
                    fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.unsorted"),
                    fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("pageable.offset"),
                    fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageable.pageNumber"),
                    fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("pageable.pageSize"),
                    fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("pageable.paged"),
                    fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("pageable.unpaged"),
                    fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("last"),
                    fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                    fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                    fieldWithPath("size").type(JsonFieldType.NUMBER).description("size"),
                    fieldWithPath("number").type(JsonFieldType.NUMBER).description("number"),
                    fieldWithPath("sort").type(JsonFieldType.OBJECT).description("sort"),
                    fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty"),
                    fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted"),
                    fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted"),
                    fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("first"),
                    fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                    fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("empty")
                )
            ));
    }

    @Test
    @DisplayName("User 등록 테스트")
    void register() throws Exception {
        UserRequest userRequest = new UserRequest("kate", 15, "flute");
        mockMvc.perform(post("/api/v1/users")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
            .andExpect(jsonPath("$.name").value(userRequest.name()))
            .andExpect(jsonPath("$.age").value(userRequest.age()))
            .andExpect(jsonPath("$.hobby").value(userRequest.hobby()))
            .andDo(document("user-register",
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                )
            ));
    }

    @Test
    @DisplayName("User 조회 테스트")
    void getOne() throws Exception {
        mockMvc.perform(get("/api/v1/users/{id}", response.id())
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
            .andExpect(jsonPath("$.id").value(response.id()))
            .andExpect(jsonPath("$.name").value(response.name()))
            .andExpect(jsonPath("$.age").value(response.age()))
            .andExpect(jsonPath("$.hobby").value(response.hobby()))
            .andDo(document("user-getOne",
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                )
            ));
    }

    @Test
    @DisplayName("User 수정 테스트")
    void modify() throws Exception {
        UserRequest userRequest = new UserRequest("moosong", 15, "flute");
        mockMvc.perform(put("/api/v1/users/{id}", response.id())
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
            .andExpect(jsonPath("$.age").value(userRequest.age()))
            .andExpect(jsonPath("$.hobby").value(userRequest.hobby()))
            .andDo(document("user-modify",
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                )
            ));
    }

    @Test
    @DisplayName("User 삭제 테스트")
    void remove() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", response.id())
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(document("user-remove"));
    }
}