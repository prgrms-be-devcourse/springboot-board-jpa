package com.assignment.board.controller;

import com.assignment.board.dto.post.PostRequestDto;
import com.assignment.board.dto.post.PostResponseDto;
import com.assignment.board.dto.post.PostUpdateDto;
import com.assignment.board.entity.Hobby;
import com.assignment.board.entity.User;
import com.assignment.board.repository.PostRepository;
import com.assignment.board.repository.UserRepository;
import com.assignment.board.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    User user;
    User savedUser;
    PostRequestDto postRequestDto;
    PostResponseDto createdPostResponseDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("김소현");
        user.setAge(23);
        user.setHobby(Hobby.SWIM);
        savedUser = userRepository.save(user);

        postRequestDto = new PostRequestDto();
        postRequestDto.setTitle("테스트");
        postRequestDto.setContent("테스트 입니다.");
        postRequestDto.setUserId(savedUser.getId());
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    void createTest() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequestDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAllTest() throws Exception {
        createdPostResponseDto = postService.createPost(postRequestDto);

        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getPostById() throws Exception {
        createdPostResponseDto = postService.createPost(postRequestDto);

        mockMvc.perform(get("/api/v1/posts/{postId}", createdPostResponseDto.getId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updatePost() throws Exception {
        createdPostResponseDto = postService.createPost(postRequestDto);

        PostUpdateDto postUpdateDto = new PostUpdateDto();
        postUpdateDto.setTitle("수정 테스트");
        postUpdateDto.setContent("수정합니다");

        mockMvc.perform(patch("/api/v1/posts/{postId}", createdPostResponseDto.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

}