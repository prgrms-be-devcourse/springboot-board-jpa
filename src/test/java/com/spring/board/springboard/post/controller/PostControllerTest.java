package com.spring.board.springboard.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.board.springboard.post.domain.dto.RequestPostDTO;
import com.spring.board.springboard.post.domain.dto.UpdatePostDTO;
import com.spring.board.springboard.post.service.PostService;
import com.spring.board.springboard.user.domain.Hobby;
import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

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
    ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(
                new Member("이수린", 24, Hobby.sleep)
        );

        RequestPostDTO requestPostDTO = new RequestPostDTO(
                "스프링 게시판 미션",
                "이 미션 끝나면 크리스마스에요",
                LocalDateTime.now(),
                1);

        postService.createPost(requestPostDTO);
    }

    @DisplayName("모든 게시물을 불러올 수 있다.")
    @Test
    void getAllPosts() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page",
                                String.valueOf(0))
                        .param("size",
                                String.valueOf(2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("게시물을 새로 생성할 수 있다.")
    @Test
    void createPost() throws Exception {
        // given
        RequestPostDTO requestPostDTO = new RequestPostDTO(
                "저장하기",
                "게시판에 새 글 저장하기",
                LocalDateTime.now(),
                1);

        // when and then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestPostDTO)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("하나의 게시물을 조회할 수 있다.")
    @Test
    void getPost() throws Exception {
        mockMvc.perform(get("/posts/{postId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("게시물의 제목과 내용을 수정할 수 있다.")
    @Test
    void updatePost() throws Exception {
        UpdatePostDTO updatePostDTO = new UpdatePostDTO(
                "수정하기",
                "게시판 새 글 저장하기 -> 수정된 내용임",
                1
        );

        mockMvc.perform(post("/posts/{postId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePostDTO)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}