package com.prgrms.hyuk.controller;

import static com.prgrms.hyuk.exception.ExceptionMessage.INVALID_POST_ID_EXP_MSG;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.hyuk.dto.PostCreateRequest;
import com.prgrms.hyuk.dto.PostDto;
import com.prgrms.hyuk.dto.PostUpdateRequest;
import com.prgrms.hyuk.dto.UserDto;
import com.prgrms.hyuk.exception.InvalidPostIdException;
import com.prgrms.hyuk.service.PostService;
import com.prgrms.hyuk.web.controller.PostController;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {PostController.class})
class PostControllerTest {

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("게시글 생성")
    void testSave() throws Exception {
        //given
        Long postId = 1L;
        given(postService.create(any(PostCreateRequest.class))).willReturn(postId);

        var postCreateRequest = new PostCreateRequest(
            "this is title ...",
            "content",
            new UserDto(
                "pang",
                26,
                "soccer"
            )
        );

        //when
        //then
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCreateRequest)))
            .andExpect(jsonPath("$.statusCode").value(HttpStatus.CREATED.value()))
            .andExpect(jsonPath("$.data").value("/posts/" + postId))
            .andExpect(jsonPath("$.serverDatetime").exists());
    }

    @Test
    @DisplayName("게시글 전체 조회")
    void testGetAll() throws Exception {
        //given
        var postDto1 = new PostDto(
            1,
            "this is title...",
            "this is content",
            "pang"
        );

        var postDto2 = new PostDto(
            2,
            "this is title...",
            "this is content",
            "pang"
        );

        given(postService.findPosts(anyInt(), anyInt())).willReturn(List.of(postDto1, postDto2));

        //when
        //then
        mockMvc.perform(get("/posts")
                .param("offset", String.valueOf(0))
                .param("limit", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value("200"))
            .andExpect(jsonPath("$.data", hasSize(2)))
            .andExpect(jsonPath("$.serverDatetime").exists());
    }

    @Test
    @DisplayName("게시글 단건 조회 - 성공")
    void testGetOneSuccess() throws Exception {
        //given
        var postDto = new PostDto(
            1,
            "this is title...",
            "this is content...",
            "pang"
        );
        given(postService.findPost(1L)).willReturn(postDto);

        //when
        //then
        mockMvc.perform(get("/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value("200"))
            .andExpect(jsonPath("$.data.id").value(String.valueOf(postDto.getId())))
            .andExpect(jsonPath("$.data.title").value(postDto.getTitle()))
            .andExpect(jsonPath("$.data.content").value(postDto.getContent()))
            .andExpect(jsonPath("$.data.userName").value(postDto.getUserName()))
            .andExpect(jsonPath("$.serverDatetime").exists());
    }

    @Test
    @DisplayName("게시글 단건 조회 - 실패")
    void testGetOneFailBecauseInvalidPostId() throws Exception {
        //given
        given(postService.findPost(anyLong())).will(
            invocation -> {
                throw new InvalidPostIdException(INVALID_POST_ID_EXP_MSG);
            }
        );

        //when
        //then
        mockMvc.perform(get("/posts/1"))
            .andExpect(jsonPath("$.statusCode").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
            .andExpect(jsonPath("$.data").value(INVALID_POST_ID_EXP_MSG.getMessage()))
            .andExpect(jsonPath("$.serverDatetime").exists());
    }

    @Test
    @DisplayName("게시글 업데이트 - 성공")
    void testUpdatePostSuccess() throws Exception {
        //given
        given(postService.updatePost(anyLong(), any(PostUpdateRequest.class)))
            .willReturn(1L);

        var id = 1L;
        var postUpdateRequest = new PostUpdateRequest(
            "this is updated title...",
            "this is updated content..."
        );

        //when
        //then
        mockMvc.perform(patch("/posts/{id}", String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postUpdateRequest)))
            .andExpect(jsonPath("$.statusCode").value("200"))
            .andExpect(jsonPath("$.data").value(String.valueOf(id)))
            .andExpect(jsonPath("$.serverDatetime").exists());
    }

    @Test
    @DisplayName("게시글 업데이트 - 실패")
    void testUpdatePostFailBecauseInvalidPostId() throws Exception {
        //given
        given(postService.updatePost(anyLong(), any(PostUpdateRequest.class)))
            .will(
                invocation -> {
                    throw new InvalidPostIdException(INVALID_POST_ID_EXP_MSG);
                }
            );

        var id = 1L;
        var postUpdateRequest = new PostUpdateRequest(
            "this is updated title...",
            "this is updated content..."
        );

        //when
        //then
        mockMvc.perform(patch("/posts/{id}", String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postUpdateRequest)))
            .andExpect(jsonPath("$.statusCode").value(HttpStatus.NOT_FOUND.value()))
            .andExpect(jsonPath("$.data").value(INVALID_POST_ID_EXP_MSG.getMessage()))
            .andExpect(jsonPath("$.serverDatetime").exists());
    }
}
