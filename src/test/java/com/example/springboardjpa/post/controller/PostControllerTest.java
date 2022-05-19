package com.example.springboardjpa.post.controller;

import com.example.springboardjpa.post.dto.PostDto;
import com.example.springboardjpa.post.service.PostService;
import com.example.springboardjpa.user.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    private long id;

    @BeforeEach
    void save() {
        PostDto postDto = new PostDto(
                10L,
                "게시판 테스트 제목",
                "안녕하세요!",
                new UserDto(
                        1L,
                        "둘리",
                        3,
                        "호잇"
                )
        );
        long saveEntity = postService.save(postDto);
        id = saveEntity;
        assertThat(saveEntity).isNotZero();
    }

    @Test
    @DisplayName("save 테스트")
    void saveTest() throws Exception {
        PostDto postDto = new PostDto(
                10L,
                "게시판 테스트 제목",
                "안녕하세요!",
                new UserDto(
                        1L,
                        "둘리",
                        3,
                        "호잇"
                )
        );

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("getOne 테스트")
    void getOneTest() throws Exception {
        mockMvc.perform(get("/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("getAll 테스트")
    void getAllTest() throws Exception {
        mockMvc.perform(get("/posts")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("updateOne 테스트")
    void updateOneTest() throws Exception {
        PostDto postDto = new PostDto(
                10L,
                "게시판 바뀐 제목",
                "안녕하세요!",
                new UserDto(
                        1L,
                        "둘리",
                        3,
                        "호잇"
                )
        );
        mockMvc.perform(post("/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}