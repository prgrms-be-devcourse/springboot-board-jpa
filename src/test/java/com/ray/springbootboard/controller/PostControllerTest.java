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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
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
        given(postService.save(any(Post.class)))
                .willReturn(1L);

        // When
        ResultActions action = mvc.perform(post("/api/v1/posts")
                .param("userId", String.valueOf(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        // Then
        action.andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/posts/1"))
                .andDo(
                        document("post/save",
                                requestFields(
                                        fieldWithPath("title").type(STRING).description("제목 입니다."),
                                        fieldWithPath("content").type(STRING).description("내용 입니다.")
                                ),
                                responseHeaders(
                                        headerWithName("Location").description("생성 후 세부 정보 redirection link")
                                )
                        )
                );
    }

    @Test
    @DisplayName("모든 포스트를 조회한다")
    void successFindAllPosts() throws Exception {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));

        Page<Post> posts = new PageImpl<>(List.of(
                new Post(1L, "title1", "content1", null, "USER1"),
                new Post(2L, "title2", "content2", null, "USER2"),
                new Post(3L, "title3", "content3", null, "USER1")
        ));
        given(postService.findAllWithPage(pageRequest)).willReturn(posts);

        // When
        ResultActions action = mvc.perform(get("/api/v1/posts")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,desc")
        );

        // Then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(3))
                .andExpect(jsonPath("$.pageData.first").value(true))
                .andExpect(jsonPath("$.pageData.last").value(true))
                .andDo(
                        document("post/getAll",
                                queryParameters(
                                        parameterWithName("page").description("페이지"),
                                        parameterWithName("size").description("페이지 내 개수"),
                                        parameterWithName("sort").description("정렬 방법((ex) id,desc)")
                                ),
                                responseFields(
                                        fieldWithPath("data").type(ARRAY).description("페이지 내 포스트 정보"),
                                        fieldWithPath("data[].id").type(NUMBER).description("페이지 내 포스트 정보"),
                                        fieldWithPath("data[].title").type(STRING).description("페이지 내 포스트 정보"),
                                        fieldWithPath("data[].content").type(STRING).description("페이지 내 포스트 정보"),
                                        fieldWithPath("data[].createdBy").type(STRING).description("페이지 내 포스트 정보"),
                                        fieldWithPath("pageData.first").type(BOOLEAN).description("첫 페이지 여부"),
                                        fieldWithPath("pageData.last").type(BOOLEAN).description("마지막 페이지 여부"),
                                        fieldWithPath("pageData.number").type(NUMBER).description("현재 페이지 개수"),
                                        fieldWithPath("pageData.size").type(NUMBER).description("페이지 수"),
                                        fieldWithPath("pageData.sort").type(OBJECT).description("페이지 정렬 방식"),
                                        fieldWithPath("pageData.sort.empty").type(BOOLEAN).description("페이지 정렬 방식"),
                                        fieldWithPath("pageData.sort.sorted").type(BOOLEAN).description("페이지 정렬 방식"),
                                        fieldWithPath("pageData.sort.unsorted").type(BOOLEAN).description("페이지 정렬 방식"),
                                        fieldWithPath("pageData.totalPages").type(NUMBER).description("총 페이지 개수"),
                                        fieldWithPath("pageData.totalElements").type(NUMBER).description("총 포스트 개수")
                                )
                        )
                );
    }

    @Test
    @DisplayName("id를 가지고 포스트를 조회한다")
    void successFindPostWithId() throws Exception {
        // Given
        Long postId = 1L;
        Post post = new Post(1L, "title", "content", null, "USER1");
        given(postService.getById(postId)).willReturn(post);

        // When
        ResultActions action = mvc.perform(get("/api/v1/posts/{id}", postId));

        // Then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"))
                .andExpect(jsonPath("$.createdBy").value("USER1"))
                .andDo(
                        document("post/getById",
                                pathParameters(
                                        parameterWithName("id").description("포스트 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(NUMBER).description("조회 된 포스트 id"),
                                        fieldWithPath("title").type(STRING).description("조회 된 포스트 title"),
                                        fieldWithPath("content").type(STRING).description("조회 된 포스트 content"),
                                        fieldWithPath("createdBy").type(STRING).description("조회 된 포스트 createdBy")
                                )
                        )
                );
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
                .andExpect(header().stringValues("Location", "/api/v1/posts/1"))
                .andDo(
                        document("post/update",
                                pathParameters(
                                        parameterWithName("id").description("포스트 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(STRING).description("수정할 제목"),
                                        fieldWithPath("content").type(STRING).description("수정할 내용")
                                ),
                                responseHeaders(
                                        headerWithName("Location").description("생성 후 세부 정보 redirection link")
                                )
                        )
                );
    }
}
