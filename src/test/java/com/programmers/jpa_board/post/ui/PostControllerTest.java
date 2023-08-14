package com.programmers.jpa_board.post.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.jpa_board.global.ApiResponse;
import com.programmers.jpa_board.global.exception.NotFoundException;
import com.programmers.jpa_board.post.application.PostServiceImpl;
import com.programmers.jpa_board.post.domain.dto.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static com.programmers.jpa_board.global.exception.ExceptionMessage.NOT_FOUND_POST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private PostServiceImpl postService;

    @Test
    void API_POST_게시물_저장_성공() throws Exception {
        //given
        PostDto.CreatePostRequest request = new PostDto.CreatePostRequest("제목", "내용", 1L);
        PostDto.CommonResponse response = new PostDto.CommonResponse(1L, "제목", "내용", 1L, "신범철", LocalDateTime.now());

        given(postService.save(request))
                .willReturn(response);

        //when & then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value("CREATED"))
                .andExpect(jsonPath("data.id").value(response.id()))
                .andExpect(jsonPath("data.title").value(response.title()))
                .andExpect(jsonPath("data.content").value(response.content()))
                .andExpect(jsonPath("data.userId").value(response.userId()))
                .andExpect(jsonPath("data.createdBy").value(response.createdBy()))
                .andExpect(jsonPath("data.createAt").isNotEmpty())
                .andDo(print());
    }

    @Test
    void API_GET_게시물_단건_조회_성공() throws Exception {
        //given
        Long postId = 1L;
        PostDto.CommonResponse response = new PostDto.CommonResponse(postId, "제목", "내용", 1L, "신범철", LocalDateTime.now());

        given(postService.getOne(postId))
                .willReturn(response);

        //when & then
        mockMvc.perform(get("/posts/{id}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value("OK"))
                .andExpect(jsonPath("data.id").value(response.id()))
                .andExpect(jsonPath("data.title").value(response.title()))
                .andExpect(jsonPath("data.content").value(response.content()))
                .andExpect(jsonPath("data.userId").value(response.userId()))
                .andExpect(jsonPath("data.createdBy").value(response.createdBy()))
                .andExpect(jsonPath("data.createAt").isNotEmpty())
                .andDo(print());
    }

    @Test
    void API_GET_게시물_단건_조회_실패() throws Exception {
        //given
        Long postId = 1L;
        ApiResponse<String> response = ApiResponse.fail(HttpStatus.NOT_FOUND, NOT_FOUND_POST.getMessage());

        given(postService.getOne(postId))
                .willThrow(new NotFoundException(NOT_FOUND_POST.getMessage()));

        //when & then
        mockMvc.perform(get("/posts/{id}", postId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value("NOT_FOUND"))
                .andExpect(jsonPath("data").value(response.getData()))
                .andDo(print());
    }

    @Test
    void API_GET_게시물_전체_조회_성공() throws Exception {
        //given
        PostDto.CommonResponse response1 = new PostDto.CommonResponse(1L, "제목1", "내용1", 1L, "신범철", LocalDateTime.now());
        PostDto.CommonResponse response2 = new PostDto.CommonResponse(2L, "제목2", "내용2", 1L, "신범철", LocalDateTime.now());

        PageRequest pageable = PageRequest.of(0, 10);
        PageImpl<PostDto.CommonResponse> responses = new PageImpl<>(List.of(response1, response2), pageable, 2);

        given(postService.getPage(any(Pageable.class)))
                .willReturn(responses);

        //when & then
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value("OK"))
                .andDo(print());
    }

    @Test
    void API_PUT_게시물_수정_성공() throws Exception {
        //given
        Long postId = 1L;
        PostDto.UpdatePostRequest updatePostRequest = new PostDto.UpdatePostRequest("변경-제목", "변경-내용");
        PostDto.CommonResponse response = new PostDto.CommonResponse(postId, "변경-제목", "변경-내용", 1L, "신범철", LocalDateTime.now());

        when(postService.update(eq(postId), any())).thenReturn(response);

        //when & then
        mockMvc.perform(put("/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePostRequest)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value("OK"))
                .andDo(print());

        verify(postService).update(eq(postId), any());
    }
}
