package com.example.board.domain.comment.controller;


import com.example.board.domain.comment.dto.CommentCreateRequest;
import com.example.board.domain.comment.dto.CommentResponse;
import com.example.board.domain.comment.dto.CommentUpdateRequest;
import com.example.board.domain.comment.service.CommentService;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.entity.Post;
import com.example.board.global.security.config.SecurityConfig;
import com.example.board.global.security.details.MemberDetails;
import com.example.board.global.security.jwt.filter.JwtFilter;
import com.example.board.global.security.jwt.provider.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.board.factory.post.PostFactory.createPost;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = CommentController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtTokenProvider.class)
    }
)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    private Post post = createPost();

    private Member member = post.getMember();

    @Test
    void 댓글_생성_호출_테스트() throws Exception {
        // Given
        CommentCreateRequest request = new CommentCreateRequest(1L, "댓글 1");
        Long expectedPostId = 1L;
        Long expectedUserId = 1L;
        Long expectedCommentId = 1L;
        MemberDetails memberDetails = new MemberDetails(Long.toString(expectedUserId),
            "username",
            "password",
            List.of());
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(memberDetails,"", memberDetails.getAuthorities()));

        given(commentService.createComment(expectedPostId, expectedUserId, request)).willReturn(expectedCommentId);

        // When & Then
        mvc.perform(post("/api/v1/comments/{postId}", post.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("comment-create",
                requestFields(
                    fieldWithPath("parentId").type(JsonFieldType.NUMBER).description("댓글 부모 아이디"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
                )
            ));
    }

    @Test
    void 댓글_전체_조회_테스트() throws Exception {
        // Given
        CommentCreateRequest request = new CommentCreateRequest(1L, "댓글 1");
        List<CommentResponse> responses = List.of(
            new CommentResponse(
                1L,
                post.getId(),
                request.content(),
                member.getName(),
                request.parentId(),
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString(),
                member.getEmail(),
                new ArrayList<>()
            )
        );
        given(commentService.findAllCommentsByPostId(post.getId())).willReturn(responses);

        // When & Then
        mvc.perform(get("/api/v1/comments/{postId}" , post.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("comment-findAll",
                responseFields(
                    fieldWithPath("[].commentId").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                    fieldWithPath("[].postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                    fieldWithPath("[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                    fieldWithPath("[].writer").type(JsonFieldType.STRING).description("댓글 작성자"),
                    fieldWithPath("[].parentId").type(JsonFieldType.NUMBER).description("댓글 부모 아이디"),
                    fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("댓글 작성 시간"),
                    fieldWithPath("[].updatedAt").type(JsonFieldType.STRING).description("댓글 수정 시간"),
                    fieldWithPath("[].updatedBy").type(JsonFieldType.STRING).description("댓글 수정자"),
                    fieldWithPath("[].children").type(JsonFieldType.ARRAY).description("하위 댓글 리스트")
                )
            ));
    }

    @Test
    void 댓글_수정_테스트() throws Exception {
        // Given
        Long commentId = 1L;
        CommentUpdateRequest request = new CommentUpdateRequest("댓글 수정입니다 !");

        // When & Then
        mvc.perform(patch("/api/v1/comments/{commentId}", commentId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(document("comment-update",
                requestFields(
                    fieldWithPath("content").type(JsonFieldType.STRING).description("수정 댓글 내용")
                )
            ));
    }
}
