package com.ray.springbootboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.springbootboard.controller.dto.PostSaveRequest;
import com.ray.springbootboard.controller.dto.PostUpdateRequest;
import com.ray.springbootboard.domain.Post;
import com.ray.springbootboard.service.PostService;
import com.ray.springbootboard.service.vo.PostUpdateInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PostService postService;

    @Test
    @DisplayName("포스트 생성에 성공한다")
    void successSavePost() throws Exception {
        // Given
        PostSaveRequest request = new PostSaveRequest("title", "content");
        Long userId = 1L;
        given(postService.save(any(Post.class), any(Long.class)))
                .willReturn(1L);

        // When
        ResultActions action = mvc.perform(post("/api/v1/posts")
                .param("userId", String.valueOf(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        // Then
        action.andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/posts/1"));
    }

    @Test
    @DisplayName("모든 포스트를 조회한다")
    void successFindAllPosts() throws Exception {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Post> posts = new PageImpl<>(List.of());
        given(postService.findAllWithPage(pageRequest)).willReturn(posts);

        // When
        ResultActions action = mvc.perform(get("/api/v1/posts")
                .param("page", "0")
                .param("size", "10"));

        // Then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(0))
                .andExpect(jsonPath("$.pageData.first").value(true))
                .andExpect(jsonPath("$.pageData.last").value(true));
    }

    @Test
    @DisplayName("id를 가지고 포스트를 조회한다")
    void successFindPostWithId() throws Exception {
        // Given
        Long postId = 1L;
        Post post = new Post(1L, "title", "content", null, null);
        given(postService.getById(postId)).willReturn(post);

        // When
        ResultActions action = mvc.perform(get("/api/v1/posts/{id}", postId));

        // Then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"));
    }


    @Test
    @DisplayName("포스트 수정에 성공한다")
    void successUpdatePost() throws Exception {
        // Given
        PostUpdateRequest request = new PostUpdateRequest("title", "content");
        Long postId = 1L;
        given(postService.update(any(PostUpdateInfo.class)))
                .willReturn(1L);

        // When
        ResultActions action = mvc.perform(put("/api/v1/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        // Then
        action.andExpect(status().isNoContent())
                .andExpect(header().stringValues("Location", "/api/v1/posts/1"));
    }
}
