package com.programmers.springboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springboard.dto.PostDto;
import com.programmers.springboard.dto.UserDto;
import com.programmers.springboard.repository.PostRepository;
import com.programmers.springboard.service.PostService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    PostDto postDto;

    @BeforeEach
    void setUp() {
        //Given
        postDto = PostDto.builder()
                .title("Test title")
                .content("Test content")
                .userDto(
                        UserDto.builder()
                                .name("Test name")
                                .age(0)
                                .hobby("Test hobby")
                                .build()
                )
                .build();
        //When
        postService.save(postDto);
    }

    @Test
    void saveTest() throws Exception {
        //Given
        postDto = PostDto.builder()
                .title("Test title")
                .content("Test content")
                .userDto(
                        UserDto.builder()
                                .name("Test name")
                                .age(0)
                                .hobby("Test hobby")
                                .build()
                )
                .build();

        // When // Then
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk()) // 200
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("postTitle"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("postContent"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("Status"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("Post Id")
                        )
                ));
    }


    @Test
    void updateTest() throws Exception {
        postDto.update("update title", "update content");

        // When // Then
        mockMvc.perform(patch("/api/posts/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk()) // 200
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("postTitle"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("postContent"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("Status"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("Post Id")
                        )
                ));
    }


    @Test
    void getOne() throws Exception {
        mockMvc.perform(get("/api/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get-one",
                        responseFields(
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("created at"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("created by"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("Status")
                        )
                ));
    }


    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/api/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteOne() throws Exception {
        mockMvc.perform(delete("/api/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
