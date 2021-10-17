package com.assignment.board.controller;

import com.assignment.board.ApiResponse;
import com.assignment.board.domain.Post;
import com.assignment.board.domain.User;
import com.assignment.board.dto.DtoConverter;
import com.assignment.board.dto.PostDto;
import com.assignment.board.dto.UserDto;
import com.assignment.board.repository.PostRepository;
import com.assignment.board.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DtoConverter dtoConverter;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void save_test() {
        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("테스트하기")
                .content("서비스 테스트 입니다.")
                .userDto(
                        UserDto.builder()
                                .name("chaewon")
                                .age(25)
                                .hobby("ddd")
                                .build()
                )
                .build();

        Long savedId = postService.save(postDto);

    }


    @Test
    void saveCallTest() throws Exception {
        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("테스트하기")
                .content("서비스 테스트 입니다.")
                .userDto(
                        UserDto.builder()
                                .name("chaewon")
                                .age(25)
                                .hobby("ddd")
                                .build()
                )
                .build();

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto))) //Json 형태의 String으로 orderDto를 변환
                .andExpect(status().isOk()) // 200
                .andDo(print());
    }

    @Test
    void getOne() throws Exception {
        mockMvc.perform(get("/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //200응답을 기대
                .andDo(print());
    }

    @Test
    void update() throws Exception {

        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("수정하기")
                .content("수정하기 테스트 입니다.")
                .userDto(
                        UserDto.builder()
                                .name("chaewon")
                                .age(25)
                                .hobby("ddd")
                                .build()
                )
                .build();

        mockMvc.perform(put("/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0)) //페이지랑 사이즈
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
