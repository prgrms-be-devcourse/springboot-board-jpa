package com.example.springbootboard.controller;

import com.example.springbootboard.domain.*;
import com.example.springbootboard.dto.CreatePostRequestDto;
import com.example.springbootboard.dto.UpdatePostRequestDto;
import com.example.springbootboard.repository.PostRepository;
import com.example.springbootboard.repository.UserRepository;
import com.example.springbootboard.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Slf4j
@SpringBootTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    private Long postId;
    private User user;

    @BeforeEach
    @DisplayName("기본 유저, 포스트 추가")
    void setup() throws NotFoundException {
        user = User.of(UUID.randomUUID().toString(), "sezi", 29, Hobby.FOOD);
        userRepository.save(user);

        postService.save("basic title", "basic content", user.getUuid());

        Post retrievedPost = postRepository.findAll().get(0);
        postId = retrievedPost.getId();
        log.info("{} {}", retrievedPost.getTitle(), retrievedPost.getContent());
    }

    @Test
    @DisplayName("페이징 조회")
    void findAllTest() throws Exception {
        postService.save("2nd title", "2nd content", user.getUuid());
        postService.save("3rd title", "3rd content", user.getUuid());

        mockMvc.perform(get("/posts")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(3))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("단건 조회")
    void findOneTest() throws Exception {
        mockMvc.perform(get("/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("작성 조회")
    void createPostTest() throws Exception {
        CreatePostRequestDto dto = new CreatePostRequestDto("2nd title", "2nd content", user.getUuid());

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());

        postRepository.findAll().forEach(p -> log.info("{} {}", p.getTitle(), p.getContent()));
    }

    @Test
    @DisplayName("수정 조회")
    void updatePostTest() throws Exception {
        UpdatePostRequestDto dto = new UpdatePostRequestDto("next title", "next content");

        mockMvc.perform(post("/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());

        Post retrievedPost = postRepository.findById(postId).get();
        log.info("{} {}", retrievedPost.getTitle(), retrievedPost.getContent());
    }
}
