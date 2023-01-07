package com.example.bulletin_board_jpa.post.controller;

import com.example.bulletin_board_jpa.post.dto.PostRequestDto;
import com.example.bulletin_board_jpa.post.dto.PutRequestDto;
import com.example.bulletin_board_jpa.post.service.PostService;
import com.example.bulletin_board_jpa.user.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
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

    // 조회하기 위해서 미리 저장해둔다
    @BeforeEach
    void save() {
        UserDto userDto1 = new UserDto("이동준", 28, "기타 치기");
        PostRequestDto postRequestDto = new PostRequestDto("오늘의 일기", "즐거웠다", userDto1);

        postService.save(postRequestDto);

        UserDto userDto2 = new UserDto("이유진", 26, "쉬기");
        PostRequestDto postRequestDto2 = new PostRequestDto("오늘의 일기", "행복했다", userDto2);

        postService.save(postRequestDto2);
    }




    @Test
    void postBoardCall() throws Exception {
        // given
        // post 요청을 위한 용도 (json 형태로 들어갈 객체이 -> ObjectMapper 를 통해 변환된다
        UserDto userDto = new UserDto("이유진", 28, "기타 치기");
        PostRequestDto postRequestDto = new PostRequestDto("오늘의 일기", "행복했다", userDto);

        // when // then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequestDto)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void getOne() throws Exception {
        mockMvc.perform(get("/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        // given
        // put 요청을 위한 새로운 게시글
        PutRequestDto putRequestDto = new PutRequestDto("가족 외식의 날", "와인을 마심");

        // when // then
        mockMvc.perform(put("/posts/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putRequestDto)))
                .andExpect(status().isOk())
                .andDo(print());

    }

}