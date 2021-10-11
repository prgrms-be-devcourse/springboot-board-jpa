package org.prgms.board.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.board.domain.entity.Comment;
import org.prgms.board.domain.entity.Post;
import org.prgms.board.domain.entity.User;
import org.prgms.board.post.dto.PostRequest;
import org.prgms.board.post.dto.PostResponse;
import org.prgms.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    private Comment comment;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(1L)
            .name("buhee")
            .age(26)
            .hobby("making")
            .build();

        post = Post.builder()
            .id(1L)
            .title("title")
            .content("content")
            .writer(user)
            .build();

        comment = Comment.builder()
            .id(1L)
            .content("comment")
            .post(post)
            .writer(user)
            .build();
        post.addComment(comment);
    }

    @DisplayName("모든 게시글 조회하기")
    @Test
    void getAllPost() throws Exception {
        List<PostResponse> postResponses = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            postResponses.add(new PostResponse(post));
        }
        Page page = new PageImpl(postResponses);
        given(postService.getAllPost(any(Pageable.class))).willReturn(page);

        RequestBuilder request = MockMvcRequestBuilders
            .get("/api/posts")
            .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("특정 게시글 조회하기")
    @Test
    void getOnePost() throws Exception {
        given(postService.getOnePost(anyLong())).willReturn(new PostResponse(post));

        RequestBuilder request = MockMvcRequestBuilders
            .get("/api/posts/{postId}", post.getId())
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
            .andExpect(status().isOk())
            .andDo(
                document("post-getOne",
                    responseFields(
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 ID(고유한 값)"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                        fieldWithPath("data.author").type(JsonFieldType.STRING).description("작성자"),
                        fieldWithPath("data.createdDate").type(JsonFieldType.NULL).description("생성날짜"),
                        fieldWithPath("data.updatedDate").type(JsonFieldType.NULL).description("변경날짜"),
                        fieldWithPath("data.comments[]").type(JsonFieldType.ARRAY).description("댓글리스트"),
                        fieldWithPath("data.comments[].id").type(JsonFieldType.NUMBER).description("댓글 ID(고유한 값)"),
                        fieldWithPath("data.comments[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                        fieldWithPath("data.comments[].author").type(JsonFieldType.STRING).description("댓글 작성자"),
                        fieldWithPath("data.comments[].createdDate").type(JsonFieldType.NULL).description("댓글 생성날짜"),
                        fieldWithPath("data.comments[].updatedDate").type(JsonFieldType.NULL).description("댓글 변경날짜"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드")
                    ))
            );
    }

    @DisplayName("게시글 등록하기")
    @Test
    void addPost() throws Exception {
        given(postService.addPost(anyLong(), any())).willReturn(post.getId());

        String body = objectMapper.writeValueAsString(
            new PostRequest("title", "content"));

        RequestBuilder request = MockMvcRequestBuilders
            .post("/api/posts/{userId}", user.getId())
            .content(body)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
            .andExpect(status().isOk())
            .andDo(
                document("post-add",
                    requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                    ),
                    responseFields(
                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드")
                    )
                )
            );
    }

    @DisplayName("게시글 수정하기")
    @Test
    void modifyPost() throws Exception {
        given(postService.modifyPost(anyLong(), anyLong(), any())).willReturn(post.getId());

        String body = objectMapper.writeValueAsString(
            new PostRequest("title", "content"));

        RequestBuilder request = MockMvcRequestBuilders
            .put("/api/posts/{userId}/{postId}", user.getId(), post.getId())
            .content(body)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
            .andExpect(status().isOk())
            .andDo(
                document("post-modify",
                    requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                    ),
                    responseFields(
                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드")
                    )
                )
            );
    }

    @DisplayName("게시글 삭제하기")
    @Test
    void removePost() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
            .delete("/api/posts/{userId}/{postId}", user.getId(), post.getId());

        this.mockMvc.perform(request)
            .andExpect(status().isOk())
            .andDo(
                document("post-remove",
                    responseFields(
                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드")
                    )
                )
            );
    }
}