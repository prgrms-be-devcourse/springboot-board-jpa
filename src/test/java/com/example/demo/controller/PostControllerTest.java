package com.example.demo.controller;

import com.example.demo.dto.PostDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    private Long savedPostId;

    @BeforeEach
    void setUpSave() {
        UserDto userDto = UserDto.builder()
                .name("jamie")
                .age(3)
                .hobby("dance")
                .build();
        PostDto postDto = PostDto.builder()
                .title("test_title")
                .content("내용입니다. contents")
                .userDto(userDto)
                .build();

        savedPostId = postService.save(postDto);

        assertThat(savedPostId).isNotZero();
    }


    @Test
    void save_test() throws Exception {
        UserDto userDto = UserDto.builder()
                .name("jamie")
                .age(3)
                .hobby("dance")
                .build();
        PostDto postDto = PostDto.builder()
                .title("test_title")
                .content("내용입니다. contents")
                .userDto(userDto)
                .build();

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getOne_test() throws Exception {
        mockMvc.perform(get("/posts/{id}", savedPostId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("없는 postId를 가져오려고하면 핸들러가 동작한다")
    void getUnknownOne_test() throws Exception {
        mockMvc.perform(get("/posts/{id}", new Random().nextLong())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andDo(print());
    }
}