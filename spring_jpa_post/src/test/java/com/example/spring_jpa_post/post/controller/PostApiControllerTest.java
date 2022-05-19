package com.example.spring_jpa_post.post.controller;

import com.example.spring_jpa_post.post.dto.request.CreatePostRequest;
import com.example.spring_jpa_post.post.dto.request.ModifyPostRequest;
import com.example.spring_jpa_post.post.service.PostService;
import com.example.spring_jpa_post.user.entity.Hobby;
import com.example.spring_jpa_post.user.entity.User;
import com.example.spring_jpa_post.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class PostApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;


    Long savedPostId;
    CreatePostRequest createPostRequest;

    @BeforeEach
    void setup() {
        User user = userRepository.save(User.builder()
                .name("강용수")
                .age(27)
                .hobby(Hobby.FOOTBALL)
                .build());
        createPostRequest = CreatePostRequest.builder()
                .title("제목입니다")
                .content("내용입니다.")
                .userId(user.getId())
                .build();
        savedPostId = postService.createPost(createPostRequest);
    }

    @Test
    void createPost() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(savedPostId + 1)))
                .andDo(print())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("User_Id")
                        )));

    }

    @Test
    void getPosts() throws Exception {
        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post_getAll",
                        responseFields(
                                fieldWithPath("content[]").type(JsonFieldType.ARRAY).description("content[]"),
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("content[].id"),
                                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("content[].title"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING).description("content[].content"),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("pageable.sort.empty"),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.sorted"),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.unsorted"),
                                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("pageable.offset"),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageable.pageNumber"),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("pageable.pageSize"),
                                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("pageable.paged"),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("pageable.unpaged"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("last"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("size"),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("number"),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty"),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted"),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted"),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("first"),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("empty")
                        )));
    }

    @Test
    void getOnePost() throws Exception {
        mockMvc.perform(get("/api/v1/posts/" + savedPostId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(savedPostId))
                .andExpect(jsonPath("title").value(createPostRequest.getTitle()))
                .andExpect(jsonPath("content").value(createPostRequest.getContent()))
                .andDo(print())
                .andDo(document("post-getOne",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        )));
    }


    @Test
    void modifyPost() throws Exception {
        ModifyPostRequest modifyPostRequest = ModifyPostRequest.builder().title("수정된 제목").content("수정된 내용").build();

        mockMvc.perform(post("/api/v1/posts/" + savedPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyPostRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-modify",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        )));
    }
}