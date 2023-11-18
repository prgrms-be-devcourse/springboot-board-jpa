package org.prgms.springbootboardjpayu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.springbootboardjpayu.dto.request.CreatePostRequest;
import org.prgms.springbootboardjpayu.dto.request.UpdatePostRequest;
import org.prgms.springbootboardjpayu.dto.response.PostResponse;
import org.prgms.springbootboardjpayu.dto.response.UserProfile;
import org.prgms.springbootboardjpayu.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PostController.class)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @DisplayName("게시물을 생성할 수 있다.")
    @Test
    void createPost() throws Exception {
        // given
        PostResponse response = createPostResponse();
        given(postService.createPost(any())).willReturn(response);

        CreatePostRequest request = new CreatePostRequest("제목", "내용", 1L);

        // when then
        mockMvc.perform(
                post("/api/v1/posts")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    @DisplayName("작성자가 없으면 게시글 생성에 실패한다.")
    @Test
    void createPostWithoutUser() throws Exception {
        // given
        PostResponse response = createPostResponse();
        given(postService.createPost(any())).willReturn(response);

        CreatePostRequest request = new CreatePostRequest("제목", "내용", null);

        // when then
        mockMvc.perform(
                post("/api/v1/posts")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @DisplayName("제목이 1 ~ 30자 범위를 초과로 게시글 생성에 실패한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void createPostWithOutOfRangeTitle(String title) throws Exception {
        // given
        PostResponse response = createPostResponse();
        given(postService.createPost(any())).willReturn(response);

        CreatePostRequest request = new CreatePostRequest(title, "내용", null);

        // when then
        mockMvc.perform(
                post("/api/v1/posts")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @DisplayName("게시글 수정에 성공한다.")
    @Test
    void updatePost() throws Exception {
        // given
        Long id = 1L;
        UpdatePostRequest updateRequest = new UpdatePostRequest("제목 수정", "내용 수정");

        PostResponse response = createPostResponse();
        given(postService.updatePost(id, updateRequest)).willReturn(response);

        // when then
        mockMvc.perform(
                patch("/api/v1/posts/{id}", id)
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @DisplayName("수정된 제목이 1 ~ 30자 범위를 초과로 게시글 수정에 실패한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void updatePostWithOutOfRangeTitle(String title) throws Exception {
        // given
        Long id = 1L;
        UpdatePostRequest updateRequest = new UpdatePostRequest(title, "내용 수정");

        PostResponse response = createPostResponse();
        given(postService.updatePost(id, updateRequest)).willReturn(response);

        // when then
        mockMvc.perform(
                patch("/api/v1/posts/{id}", id)
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    private static PostResponse createPostResponse() {
        PostResponse response = PostResponse.builder()
                .id(1L)
                .title("제목")
                .user(UserProfile.builder().build())
                .build();
        return response;
    }
}