package org.prgms.springbootboardjpayu.controller.docs;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootboardjpayu.controller.PostController;
import org.prgms.springbootboardjpayu.dto.request.CreatePostRequest;
import org.prgms.springbootboardjpayu.dto.response.PostResponse;
import org.prgms.springbootboardjpayu.dto.response.UserProfile;
import org.prgms.springbootboardjpayu.service.PostService;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerDocsTest extends RestDocsSupport{

    private final PostService postService = mock(PostService.class);

    @Override
    protected Object initController() {
        return new PostController(postService);
    }

    @DisplayName("게시물을 생성하는 API")
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

    private static PostResponse createPostResponse() {
        PostResponse response = PostResponse.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .createdAt(LocalDateTime.now())
                .user(UserProfile.builder().id(1L).name("의진").build())
                .build();
        return response;
    }
}