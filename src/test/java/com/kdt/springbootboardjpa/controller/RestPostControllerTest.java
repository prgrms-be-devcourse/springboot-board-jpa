package com.kdt.springbootboardjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.springbootboardjpa.PostService;
import com.kdt.springbootboardjpa.domain.dto.PostCreateRequest;
import com.kdt.springbootboardjpa.repository.PostRepository;
import com.kdt.springbootboardjpa.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RestPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService service;

    @Autowired
    private ObjectMapper objecctMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @DisplayName("ID를 통한 GET post")
    void getPostByIdTest() throws Exception {

        //when
        var dto = service.findPost(1L);

        //then
        mockMvc.perform(get("/api/v1/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value(dto.getUsername()))
                .andExpect(jsonPath("title").value(dto.getTitle()))
                .andExpect(jsonPath("content").value(dto.getContent()))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("POST 테스트")
    void makePostTest() throws Exception {

        //given
        String body = objecctMapper.writeValueAsString(
                new PostCreateRequest("test-title", "--", "hj"));

        //then
        mockMvc.perform(post("/api/v1/posts")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("생성 성공")))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("게시글 수정 테스트")
    void editPostTest() throws Exception{

        //given
        var dto = service.findPost(1L);
        dto.setTitle("update-title");

        var body = objecctMapper.writeValueAsString(dto);

        //then
        mockMvc.perform(post("/api/v1/posts/1")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("수정 성공")))
                .andDo(print());

    }

    @Test
    @Transactional
    @DisplayName("사용자 NotFound로 일어난 인가되지 않은 동작 예외 테스트 ")
    void unAuthorizationAccessExceptionTest() throws Exception{

        //given
        var request = new PostCreateRequest("access-exception", "==", "none");
        var body = objecctMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/posts")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("status").value(401))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("잘못된 인자 입력으로 일어난 PostNotFound 예외 테스트")
    void postNotFoundExceptionTest() throws Exception{

        //getPost
        mockMvc.perform(get("/api/v1/posts/10"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value(HttpStatus.NOT_FOUND.name()))
                .andExpect(jsonPath("status").value(404))
                .andDo(print());

        //editPost
        var dto = service.findPost(1L);
        dto.setTitle("update-title-exeption");
        var body = objecctMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/posts/10")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value(HttpStatus.NOT_FOUND.name()))
                .andExpect(jsonPath("status").value(404))
                .andDo(print());
    }

}