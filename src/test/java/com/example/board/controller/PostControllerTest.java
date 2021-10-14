package com.example.board.controller;


import com.example.board.dto.PostDto;
import com.example.board.dto.UserDto;
import com.example.board.repository.PostRepository;
import com.example.board.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @BeforeAll
    void saveUser(){
    }

    @Order(1)
    @Test
    void save() throws Exception {
        PostDto postDto = PostDto.builder()
                .content("안녕하세요")
                .title(("2번 포스트"))
                .userDto(
                        UserDto.builder()
                                .name("홍길동")
                                .age(26)
                                .hobby("SPORTS")
                                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                                .createdBy("박건희")
                                .build()
                )
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                .createdBy("박건희")
                .build();

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Order(2)
    @Test
    void getOne() throws Exception {
        PostDto postDto= postService.find(1);

        mockMvc.perform(get("/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAll() {
    }


}