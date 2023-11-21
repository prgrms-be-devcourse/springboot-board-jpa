package org.prgms.springbootboardjpayu.controller.docs;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootboardjpayu.controller.PostController;
import org.prgms.springbootboardjpayu.dto.request.CreatePostRequest;
import org.prgms.springbootboardjpayu.dto.response.ListResponse;
import org.prgms.springbootboardjpayu.dto.response.PostResponse;
import org.prgms.springbootboardjpayu.dto.response.UserProfile;
import org.prgms.springbootboardjpayu.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerDocsTest extends RestDocsSupport{

    private final PostService postService = mock(PostService.class);

    @Override
    protected Object initController() {
        return new PostController(postService);
    }

    @DisplayName("게시물 생성 API")
    @Test
    void createPost() throws Exception {
        // given
        PostResponse response = createPostResponse(1L);
        given(postService.createPost(any())).willReturn(response);

        CreatePostRequest request = new CreatePostRequest("제목", "내용", 1L);

        // when then
        mockMvc.perform(
                post("/api/v1/posts")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("title").type(STRING).description("Title"),
                                fieldWithPath("content").type(STRING).description("Content"),
                                fieldWithPath("userId").type(NUMBER).description("User Id")
                        ),
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("Post Id"),
                                fieldWithPath("title").type(STRING).description("Title"),
                                fieldWithPath("content").type(STRING).description("Content"),
                                fieldWithPath("createdAt").type(ARRAY).description("Created At"),
                                fieldWithPath("user").type(OBJECT).description("User"),
                                fieldWithPath("user.id").type(NUMBER).description("User Id"),
                                fieldWithPath("user.name").type(STRING).description("User Name")
                        )
                        ));
    }

    @DisplayName("게시물 다건 조회 API")
    @Test
    void getPosts() throws Exception {
        // given
        List<PostResponse> posts = Arrays.asList(
            createPostResponse(1L),
            createPostResponse(2L)
        );
        Pageable pageable = PageRequest.of(0, 5);
        Page<PostResponse> response = new PageImpl<>(posts, pageable, posts.size());

        given(postService.getPosts(pageable)).willReturn(ListResponse.builder()
                .content(posts)
                .pageNumber(response.getNumber())
                .pageSize(response.getSize())
                .totalPages(response.getTotalPages())
                .totalElements(response.getNumberOfElements())
                .build());

        // when then
        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("posts-retrieve",
                        queryParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("페이지 크기")
                        ),
                        responseFields(
                                fieldWithPath("content[].id").type(NUMBER).description("Post Id"),
                                fieldWithPath("content[].title").type(STRING).description("Title"),
                                fieldWithPath("content[].content").type(STRING).description("Content"),
                                fieldWithPath("content[].createdAt").type(ARRAY).description("Created At"),
                                fieldWithPath("content[].user").type(OBJECT).description("User"),
                                fieldWithPath("content[].user.id").type(NUMBER).description("User Id"),
                                fieldWithPath("content[].user.name").type(STRING).description("User Name"),
                                fieldWithPath("pageNumber").type(JsonFieldType.NUMBER).description("pageNumber"),
                                fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("pageSize"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("totalElements")
                        )
                ));
    }


    private static PostResponse createPostResponse(Long id) {
        PostResponse response = PostResponse.builder()
                .id(id)
                .title("제목")
                .content("내용")
                .createdAt(LocalDateTime.now())
                .user(UserProfile.builder().id(1L).name("의진").build())
                .build();
        return response;
    }
}