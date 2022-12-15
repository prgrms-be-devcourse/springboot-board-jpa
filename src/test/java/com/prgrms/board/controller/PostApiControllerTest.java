package com.prgrms.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.board.dto.MemberCreateDto;
import com.prgrms.board.dto.PostCreateDto;
import com.prgrms.board.dto.PostUpdateDto;
import com.prgrms.board.service.MemberService;
import com.prgrms.board.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
class PostApiControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PostService postService;
    private Long savedMemberId;
    private Long savedPostId;
    private PostCreateDto postCreateDto;

    @BeforeEach
    void setup() {
        MemberCreateDto createDto = MemberCreateDto.builder()
                .name("member1")
                .age(26)
                .hobby("youTube")
                .build();

        savedMemberId = memberService.join(createDto);

        postCreateDto = PostCreateDto.builder()
                .title("this is title")
                .content("this is content")
                .writerId(savedMemberId)
                .build();

        savedPostId = postService.register(postCreateDto);
    }

    @Test
    void 게시글_저장() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 게시글_단건_조회() throws Exception {
        mockMvc.perform(get("/api/v1/posts/{id}", savedPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedPostId)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 게시글_수정() throws Exception {
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .postId(savedPostId)
                .title("change title")
                .content("change content")
                .build();

        mockMvc.perform(put("/api/v1/posts", postUpdateDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 게시글_페이징_조회() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();


        mockMvc.perform(get("/api/v1/posts")
                        .param("cursorId", String.valueOf(15))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(multiValueMap)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}