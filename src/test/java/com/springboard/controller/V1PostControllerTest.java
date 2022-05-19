package com.springboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboard.post.dto.CreatePostRequest;
import com.springboard.post.dto.CreatePostResponse;
import com.springboard.post.dto.UpdatePostRequest;
import com.springboard.post.repository.PostRepository;
import com.springboard.post.service.PostService;
import com.springboard.user.dto.UserRequest;
import com.springboard.user.dto.UserResponse;
import com.springboard.user.entity.User;
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
@DisplayName("Post API 테스트")
class V1PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    UserResponse userResponse;
    CreatePostResponse response;

    @BeforeEach
    void setUp() {
        UserRequest userRequest = new UserRequest("moosong", 25, "viola");
        userResponse = userService.save(userRequest);
        CreatePostRequest request = new CreatePostRequest(userResponse.id(), "title", "content");
        response = postService.save(request);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Post 목록 조회 테스트")
    void getAll() throws Exception {
        mockMvc.perform(get("/api/v1/posts")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
            .andExpect(jsonPath("$.content[0].id").value(response.id()))
            .andDo(document("post-getAll",
                responseFields(
                    fieldWithPath("content[]").type(JsonFieldType.ARRAY).description("content[]"),
                    fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("content[].id"),
                    fieldWithPath("content[].title").type(JsonFieldType.STRING).description("content[].title"),
                    fieldWithPath("content[].content").type(JsonFieldType.STRING).description("content[].content"),
                    fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("content[].createdAt"),
                    fieldWithPath("content[].updatedAt").type(JsonFieldType.STRING).description("content[].updatedAt"),
                    fieldWithPath("content[].user").type(JsonFieldType.OBJECT).description("content[].user"),
                    fieldWithPath("content[].user.id").type(JsonFieldType.NUMBER).description("content[].user.id"),
                    fieldWithPath("content[].user.name").type(JsonFieldType.STRING).description("content[].user.name"),
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
    @DisplayName("Post 등록 테스트")
    void register() throws Exception {
        UserRequest userRequest2 = new UserRequest("moosong", 25, "viola");
        UserResponse userResponse2 = userService.save(userRequest2);
        CreatePostRequest request2 = new CreatePostRequest(userResponse2.id(), "title2", "content2");
        mockMvc.perform(post("/api/v1/posts")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
            .andExpect(jsonPath("$.user.id").value(request2.userId()))
            .andExpect(jsonPath("$.title").value(request2.title()))
            .andExpect(jsonPath("$.content").value(request2.content()))
            .andDo(document("post-register",
                requestFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("user").type(JsonFieldType.OBJECT).description("user"),
                    fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("user.id"),
                    fieldWithPath("user.name").type(JsonFieldType.STRING).description("user.name"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt")
                )
            ));
    }

    @Test
    @DisplayName("Post 조회 테스트")
    void getOne() throws Exception {
        mockMvc.perform(get("/api/v1/posts/{id}", response.id())
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
            .andExpect(jsonPath("$.id").value(response.id()))
            .andExpect(jsonPath("$.title").value(response.title()))
            .andExpect(jsonPath("$.content").value(response.content()))
            .andDo(document("post-getOne",
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("user").type(JsonFieldType.OBJECT).description("user"),
                    fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("user.id"),
                    fieldWithPath("user.name").type(JsonFieldType.STRING).description("user.name"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                )
            ));
    }

    @Test
    @DisplayName("Post 수정 테스트")
    void modify() throws Exception {
        UpdatePostRequest updateRequest = new UpdatePostRequest("title2", "content2");
        mockMvc.perform(put("/api/v1/posts/{id}", response.id())
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
            .andExpect(jsonPath("$.title").value(updateRequest.title()))
            .andExpect(jsonPath("$.content").value(updateRequest.content()))
            .andDo(document("post-modify",
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("user").type(JsonFieldType.OBJECT).description("user"),
                    fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("user.id"),
                    fieldWithPath("user.name").type(JsonFieldType.STRING).description("user.name"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                )
            ));
    }

    @Test
    @DisplayName("Post 삭제 테스트")
    void remove() throws Exception {
        mockMvc.perform(delete("/api/v1/posts/{id}", response.id())
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(document("post-remove"));
    }
}