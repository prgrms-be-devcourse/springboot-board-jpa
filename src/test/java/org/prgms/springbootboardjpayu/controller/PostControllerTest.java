package org.prgms.springbootboardjpayu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.springbootboardjpayu.dto.request.CreatePostRequest;
import org.prgms.springbootboardjpayu.dto.request.UpdatePostRequest;
import org.prgms.springbootboardjpayu.dto.response.ListResponse;
import org.prgms.springbootboardjpayu.dto.response.PostResponse;
import org.prgms.springbootboardjpayu.dto.response.UserProfile;
import org.prgms.springbootboardjpayu.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @DisplayName("게시물 생성에 성공한다.")
    @Test
    void createPost() throws Exception {
        // given
        PostResponse response = createPostResponse(1L);
        given(postService.createPost(any(CreatePostRequest.class))).willReturn(response);

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
        PostResponse response = createPostResponse(1L);
        given(postService.createPost(any(CreatePostRequest.class))).willReturn(response);

        CreatePostRequest request = new CreatePostRequest("제목", "내용", null);

        // when then
        mockMvc.perform(
                post("/api/v1/posts")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @DisplayName("제목이 1 ~ 30자 범위를 초과해 게시글 생성에 실패한다.")
    @ParameterizedTest(name = "{index}. {0} 제목의 글자 수 범위를 초과한다.")
    @EmptySource
    @MethodSource("provideLongTitle")
    void createPostWithOutOfRangeTitle(String outOfLengthTitle) throws Exception {
        // given
        PostResponse response = createPostResponse(1L);
        given(postService.createPost(any(CreatePostRequest.class))).willReturn(response);

        CreatePostRequest request = new CreatePostRequest(outOfLengthTitle, "내용", null);

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
        UpdatePostRequest updateRequest = new UpdatePostRequest("제목 수정", "내용 수정");

        PostResponse response = createPostResponse(1L);
        given(postService.updatePost(response.id(), updateRequest)).willReturn(response);

        // when then
        mockMvc.perform(
                patch("/api/v1/posts/{id}", response.id())
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @DisplayName("수정된 제목이 1 ~ 30자 범위를 초과로 게시글 수정에 실패한다.")
    @ParameterizedTest
    @EmptySource
    @MethodSource("provideLongTitle")
    void updatePostWithOutOfRangeTitle(String outOfLengthTitle) throws Exception {
        // given
        UpdatePostRequest updateRequest = new UpdatePostRequest(outOfLengthTitle, "내용 수정");

        PostResponse response = createPostResponse(1L);
        given(postService.updatePost(response.id(), updateRequest)).willReturn(response);

        // when then
        mockMvc.perform(
                patch("/api/v1/posts/{id}", response.id())
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @DisplayName("게시물 ID로 게시물 조회에 성공한다.")
    @Test
    void getPost() throws Exception {
        // given
        PostResponse response = createPostResponse(1L);
        given(postService.getPost(response.id())).willReturn(response);

        // when then
        mockMvc.perform(
                get("/api/v1/posts/{id}", response.id())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @DisplayName("전체 게시물 조회에 성공한다.")
    @Test
    void getPosts() throws Exception {
        // given
        ListResponse responses = ListResponse.builder().build();
        given(postService.getPosts(any(Pageable.class))).willReturn(responses);
        PageRequest pageRequest = PageRequest.of(0, 5);

        // when then
        mockMvc.perform(
                get("/api/v1/posts")
                        .content(objectMapper.writeValueAsString(pageRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    private static PostResponse createPostResponse(Long id) {
        return PostResponse.builder()
                .id(id)
                .title("제목")
                .content("내용")
                .createdAt(LocalDateTime.now())
                .user(UserProfile.builder().id(1L).name("의진").build())
                .build();
    }

    private static List<String> provideLongTitle() {
        String string = "a";
        return List.of(string.repeat(31));
    }
}