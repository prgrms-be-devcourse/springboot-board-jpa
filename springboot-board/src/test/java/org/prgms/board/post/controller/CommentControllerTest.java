package org.prgms.board.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.board.domain.entity.Comment;
import org.prgms.board.domain.entity.Post;
import org.prgms.board.domain.entity.User;
import org.prgms.board.post.dto.CommentRequest;
import org.prgms.board.post.service.CommentService;
import org.prgms.board.user.dto.UserIdRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

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

    @DisplayName("댓글 등록하기")
    @Test
    void addComment() throws Exception {
        given(commentService.writeComment(anyLong(), any())).willReturn(comment.getId());

        String body = objectMapper.writeValueAsString(
            new CommentRequest(1L, "comment"));

        RequestBuilder request = MockMvcRequestBuilders
            .post("/posts/{id}/comments", post.getId())
            .content(body)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
            .andExpect(status().isOk())
            .andDo(
                document("comment-add",
                    requestFields(
                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("사용자 식별자"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("댓글")
                    ),
                    responseFields(
                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드")
                    )
                )
            );
    }

    @DisplayName("댓글 수정하기")
    @Test
    void modifyComment() throws Exception {
        given(commentService.modifyComment(anyLong(), anyLong(), any())).willReturn(comment.getId());

        String body = objectMapper.writeValueAsString(
            new CommentRequest(1L, "test comment"));

        RequestBuilder request = MockMvcRequestBuilders
            .put("/posts/{postId}/comments/{commentId}", post.getId(), comment.getId())
            .content(body)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
            .andExpect(status().isOk())
            .andDo(
                document("comment-modify",
                    requestFields(
                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("사용자 식별자"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("댓글")
                    ),
                    responseFields(
                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드")
                    )
                )
            );

    }

    @DisplayName("댓글 삭제하기")
    @Test
    void removeComment() throws Exception {
        String body = objectMapper.writeValueAsString(
            new UserIdRequest(1L));

        RequestBuilder request = MockMvcRequestBuilders
            .delete("/posts/{postId}/comments/{commentId}", post.getId(), comment.getId())
            .content(body)
            .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
            .andExpect(status().isOk())
            .andDo(
                document("comment-remove",
                    requestFields(
                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("사용자 식별자")
                    ),
                    responseFields(
                        fieldWithPath("data").type(JsonFieldType.NULL).description("데이터"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드")
                    )
                )
            );
    }
}