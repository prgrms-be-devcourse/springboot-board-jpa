package com.prgrms.board.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.board.post.dto.PostRequest;
import com.prgrms.board.post.dto.PostResponse;
import com.prgrms.board.post.repository.PostRepository;
import com.prgrms.board.post.service.PostService;
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
class PostControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    UserResponse userResponse;
    PostResponse postResponse;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setup() {
        UserRequest userRequest = new UserRequest(
                "dhkstn",
                27L,
                "soccer"
        );
        userResponse = userService.insert(userRequest);
        PostRequest postRequest = new PostRequest(
                "devCourse",
                "hello"
        );
        PostRequest postRequest2 = new PostRequest(
                "devCourse",
                "world"
        );
        postResponse = postService.insert(postRequest, userResponse.getUserId());
        postService.insert(postRequest2, userResponse.getUserId());
        Assertions.assertThat(userResponse.getUserId()).isNotNull();
        Assertions.assertThat(postResponse.getPostId()).isNotNull();
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void post_저장() throws Exception {
        PostRequest postRequest = new PostRequest(
                "devCourse",
                "hello"
        );

        mockMvc.perform(post("/api/post/{userId}", userResponse.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("createdBy"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDateTime")
                        )
                ));
    }

    @Test
    void post_조회() throws Exception {
        mockMvc.perform(get("/api/post/{postId}", postResponse.getPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getOne",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("createdBy"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDateTime")
                        )));
    }

    @Test
    void postAll_조회() throws Exception {
        mockMvc.perform(get("/api/post",
                        param("keyword", ""),
                        param("page", "0"),
                        param("size", "3"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getAll",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("content"),
                                fieldWithPath("data.content[].postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.content[].createdBy").type(JsonFieldType.STRING).description("createdBy"),
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
                        )));
    }

    @Test
    void post_업데이트() throws Exception {
        PostRequest postRequest = new PostRequest(
                "null",
                "update-field"
        );

        mockMvc.perform(put("/api/post/{postId}", postResponse.getPostId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("createdBy"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDateTime")
                        )
                ));
    }

    @Test
    void post_삭제() throws Exception {
        mockMvc.perform(delete("/api/post/{postId}", postResponse.getPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-delete",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("data"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDateTime")
                        )));
    }
}