package com.prgms.jpaBoard.domain.post.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgms.jpaBoard.domain.post.application.PostService;
import com.prgms.jpaBoard.domain.post.application.dto.PostResponse;
import com.prgms.jpaBoard.domain.post.application.dto.PostResponses;
import com.prgms.jpaBoard.domain.post.presentation.dto.PostSaveRequest;
import com.prgms.jpaBoard.domain.post.presentation.dto.PostUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    @DisplayName("게시글을 생성 할 수 있다.")
    void createPost() throws Exception {
        // Given
        PostSaveRequest postSaveRequest = new PostSaveRequest("제목", "내용", 1L);

        given(postService.post(postSaveRequest)).willReturn(1L);

        // When

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postSaveRequest))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 하나를 조회 할 수 있다.")
    void readPost() throws Exception {
        // Given
        PostResponse postResponse = new PostResponse(
                1L,
                "title",
                "content",
                ZonedDateTime.now(ZoneId.of("Asia/Seoul")),
                "Writer");

        given(postService.readOne(1L)).willReturn(postResponse);

        // When

        // Then
        mockMvc.perform(get("/api/v1/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글을 전체 조회 할 수 있다.")
    void readPosts() throws Exception {
        // Given

        PostResponse postResponseA = new PostResponse(
                1L,
                "titleA",
                "contentA",
                ZonedDateTime.now(ZoneId.of("Asia/Seoul")),
                "Writer");

        PostResponse postResponseB = new PostResponse(
                2L,
                "titleB",
                "contentB",
                ZonedDateTime.now(ZoneId.of("Asia/Seoul")),
                "Writer");

        PostResponse postResponseC = new PostResponse(
                3L,
                "titleC",
                "contentC",
                ZonedDateTime.now(ZoneId.of("Asia/Seoul")),
                "Writer");

        PostResponse postResponseD = new PostResponse(
                4L,
                "titleD",
                "contentD",
                ZonedDateTime.now(ZoneId.of("Asia/Seoul")),
                "Writer");

        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id"));

        given(postService.readAll(pageRequest))
                .willReturn(new PostResponses(List.of(postResponseA, postResponseB, postResponseC, postResponseD)));

        // When

        // Then
        mockMvc.perform(get("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .param("page", "0")
                        .param("size", "20")
                        .param("sort", "id,desc"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글을 수정할 수 있다.")
    void updatePost() throws Exception {
        // Given
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest("ModifiedTitle", "ModifiedContent");

        PostResponse postResponse = new PostResponse(
                1L,
                "ModifiedTitle",
                "ModifiedContent",
                ZonedDateTime.now(ZoneId.of("Asia/Seoul")),
                "Writer"
        );

        given(postService.update(1L, postUpdateRequest)).willReturn(postResponse);

        // When

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateRequest))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}