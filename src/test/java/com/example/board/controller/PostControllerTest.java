package com.example.board.controller;

import com.example.board.dto.PostDto;
import com.example.board.repository.PostRepository;
import com.example.board.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글이 정상정으로 생성 및 업로드된다")
    void postUpload() throws Exception {
        PostDto postDto = PostDto.builder()
                .title("Test Post 1")
                .content("Content of Test Post 1")
                .createdBy("Test User 1")
                .build();

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("하나의 게시글 정보를 받아올 수 있다")
    void getOnePost() throws Exception {
        PostDto postDto = PostDto.builder()
                .title("Test Post 1")
                .content("Content of Test Post 1")
                .createdBy("Test User 1")
                .build();
        Long id = postService.save(postDto);

        mockMvc.perform(get("/api/v1/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("모든 게시글 정보를 받아올 수 있다")
    void getAllPost() throws Exception {
        PostDto postDto1 = PostDto.builder()
                .title("Test Post 1")
                .content("Content of Test Post 1")
                .createdBy("Test User 1")
                .build();
        PostDto postDto2 = PostDto.builder()
                .title("Test Post 2")
                .content("Content of Test Post 2")
                .createdBy("Test User 2")
                .build();
        PostDto postDto3 = PostDto.builder()
                .title("Test Post 3")
                .content("Content of Test Post 3")
                .createdBy("Test User 3")
                .build();
        postService.save(postDto1);
        postService.save(postDto2);
        postService.save(postDto3);

        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글을 정상적으로 수정할 수 있다")
    void editPost() throws Exception {
        PostDto postDto1 = PostDto.builder()
                .title("Test Post 1")
                .content("Content of Test Post 1")
                .createdBy("Test User 1")
                .build();
        Long postId = postService.save(postDto1);

        PostDto dtoForUpdate = PostDto.builder()
                .title("Updated Title")
                .content("Updated Content")
                .build();

        mockMvc.perform(post("/api/v1/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoForUpdate))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}