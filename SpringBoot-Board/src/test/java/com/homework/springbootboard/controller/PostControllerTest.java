package com.homework.springbootboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.springbootboard.dto.PostDto;
import com.homework.springbootboard.dto.UserDto;
import com.homework.springbootboard.repository.PostRepository;
import com.homework.springbootboard.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
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
    void getPost() throws Exception {
        log.info(String.valueOf(postDto.getId()));
        log.info("Id : {}", id);
        mockMvc.perform(get("/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getPosts() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void createPost() throws Exception {
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
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
                .andDo(print());
    }
}