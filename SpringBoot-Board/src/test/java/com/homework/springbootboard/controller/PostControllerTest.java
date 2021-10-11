package com.homework.springbootboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.springbootboard.dto.PostDto;
import com.homework.springbootboard.dto.UserDto;
import com.homework.springbootboard.repository.PostRepository;
import com.homework.springbootboard.service.PostService;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    Long id;

    PostDto postDto;

    @BeforeEach
    void setUp() {
        postDto = PostDto.builder()
                .title("test-title")
                .content("test-content")
                .userDto(
                        UserDto.builder()
                                .name("test-name")
                                .age(28)
                                .hobby("test-hobby")
                                .build()
                )
                .build();

        id = postService.save(postDto);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시물을 단일 조회할 수 있다.")
    void getPost() throws Exception {
        log.info(String.valueOf(postDto.getId()));
        log.info("Id : {}", id);
        mockMvc.perform(get("/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get",

                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.user").type(JsonFieldType.OBJECT).description("user"),
                                fieldWithPath("data.user.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.user.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("data.user.age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("data.user.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("data.user.post").type(JsonFieldType.NULL).description("post"),

                                fieldWithPath("errorStatus").type(JsonFieldType.NULL).description("errorStatus"),
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("code"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("responseData"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("responseTime")
                        )
                ));
    }

    @Test
    @DisplayName("게시물을 전체 조회할 수 있다.")
    void getPosts() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("posts-get",
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("Posts"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.content[].user").type(JsonFieldType.OBJECT).description("user"),
                                fieldWithPath("data.content[].user.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.content[].user.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("data.content[].user.age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("offset"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageNumber"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("pageSize"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("paged"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("unpaged"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("last"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("size"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("number"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsorted"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("first"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                                fieldWithPath("errorStatus").type(JsonFieldType.NULL).description("errorStatus"),
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("code"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("responseData"),
                                fieldWithPath("data.content[].user.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("data.content[].user.post").type(JsonFieldType.NULL).description("post"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("responseTime")
                        )
                ));

    }

    @Test
    @DisplayName("게시물을 생성할 수 있다.")
    void createPost() throws Exception {
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("user").type(JsonFieldType.OBJECT).description("user"),
                                fieldWithPath("user.id").type(JsonFieldType.NULL).description("user.id"),
                                fieldWithPath("user.name").type(JsonFieldType.STRING).description("user.name"),
                                fieldWithPath("user.age").type(JsonFieldType.NUMBER).description("user.age"),
                                fieldWithPath("user.hobby").type(JsonFieldType.STRING).description("user.hobby"),
                                fieldWithPath("user.post").type(JsonFieldType.NULL).description("user.post")

                        ),
                        responseFields(
                                fieldWithPath("errorStatus").type(JsonFieldType.NULL).description("errorStatus"),
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("code"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("responseData"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("responseTime")
                        )
                ));
    }

    @Test
    @DisplayName("게시물을 수정할 수 있다.")
    void updatePost() throws Exception {
        PostDto updateDto = PostDto.builder()
                .title("update-title")
                .content("update-content")
                .userDto(
                        UserDto.builder()
                                .name("test-name")
                                .age(28)
                                .hobby("test-hobby")
                                .build()
                )
                .build();

        id = postService.save(postDto);
        mockMvc.perform(post("/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("user").type(JsonFieldType.OBJECT).description("user"),
                                fieldWithPath("user.id").type(JsonFieldType.NULL).description("user.id"),
                                fieldWithPath("user.name").type(JsonFieldType.STRING).description("user.name"),
                                fieldWithPath("user.age").type(JsonFieldType.NUMBER).description("user.age"),
                                fieldWithPath("user.hobby").type(JsonFieldType.STRING).description("user.hobby"),
                                fieldWithPath("user.post").type(JsonFieldType.NULL).description("user.post")

                        ),
                        responseFields(
                                fieldWithPath("errorStatus").type(JsonFieldType.NULL).description("errorStatus"),
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("code"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("responseData"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("responseTime")
                        )
                ));
    }
}