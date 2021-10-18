package com.prgrms.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.board.domain.Post;
import com.prgrms.board.domain.User;
import com.prgrms.board.dto.IdResponse;
import com.prgrms.board.dto.post.PostCreateRequest;
import com.prgrms.board.dto.post.PostFindResponse;
import com.prgrms.board.dto.post.PostModifyRequest;
import com.prgrms.board.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("dahee")
                .age(99)
                .hobby("노래듣기")
                .build();

        post = Post.builder()
                .id(1L)
                .user(user)
                .title("title")
                .content("content")
                .build();
    }

    @DisplayName("게시글 등록하기")
    @Test
    void createPostTest() throws Exception {
        given(postService.createPost(any(PostCreateRequest.class))).willReturn(IdResponse.from(post.getId()));

        String body = objectMapper.writeValueAsString(
                new PostCreateRequest(1L, "title", "content"));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/posts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(
                        document("create-post",
                                requestFields(
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id")
                                )
                        )
                );
    }

    @DisplayName("특정 게시글 조회하기")
    @Test
    void findPostTest() throws Exception {
        given(postService.findPost(anyLong())).willReturn(PostFindResponse.from(post));

        RequestBuilder request = RestDocumentationRequestBuilders
                .get("/posts/{postId}", post.getId())
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(
                        document("find-post",
                                pathParameters(
                                        parameterWithName("postId").description("postId")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                        fieldWithPath("createdAt").type(JsonFieldType.NULL).description("createdAt"),
                                        fieldWithPath("updatedAt").type(JsonFieldType.NULL).description("updatedAt")
                                )
                        )
                );
    }

    @DisplayName("게시글 수정하기")
    @Test
    void modifyPostTest() throws Exception {
        given(postService.modifyPost(anyLong(), any(PostModifyRequest.class))).willReturn(IdResponse.from(post.getId()));

        String body = objectMapper.writeValueAsString(PostModifyRequest.from("안녕하세요", "반갑습니다"));

        RequestBuilder request = MockMvcRequestBuilders
                .put("/posts/{postId}", post.getId())
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(
                        document("modify-post",
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id")
                                )
                        )
                );
    }

    @DisplayName("게시글 삭제하기")
    @Test
    void removePostTest() throws Exception {
        given(postService.removePost(anyLong())).willReturn(IdResponse.from(post.getId()));

        RequestBuilder request = RestDocumentationRequestBuilders
                .delete("/posts/{postId}", post.getId())
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(
                        document("remove-post",
                                pathParameters(
                                        parameterWithName("postId").description("postId")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id")
                                )
                        )
                );
    }
}