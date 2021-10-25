package com.kdt.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.board.dto.comment.CommentRequest;
import com.kdt.board.dto.post.PostRequest;
import com.kdt.board.dto.user.UserRequest;
import com.kdt.board.service.CommentService;
import com.kdt.board.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;


    @Autowired
    ObjectMapper objectMapper;

    private static UserRequest userRequest;
    private static PostRequest postRequest;
    private static CommentRequest commentRequest;
    private static Long setPostId;
    private static Long setCommentId;
    private static Long setPostUserId;
    private static Long setCommentUserId;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest("set post user");

        postRequest = new PostRequest("set post title", "set post content", userRequest.getName());

        commentRequest = new CommentRequest("set comment content", "set comment user");

        setPostId = postService.createPost(postRequest);

        setCommentId = commentService.createComment(setPostId, commentRequest);

        setPostUserId = postService.findOnePost(setPostId).get().getUser().getId();

        setCommentUserId = commentService.findOneComment(setCommentId).get().getUser().getId();

    }

    // question : 어느 controller test 안에 있어야 되는거지?
    // solved : 갖고 오는 객체가 주체
    @Test
    void getCommentsByPost() throws Exception {
        mockMvc.perform(get("/posts/{postId}/comments", setCommentUserId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void createComment() throws Exception {
        mockMvc.perform(post("/posts/{postId}/comments", setPostId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CommentRequest("new comment content", "new comment user"))))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updateComment() throws Exception {
        mockMvc.perform(put("/posts/{postId}/comments/{commentId}", setPostId, setCommentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CommentRequest("updated comment content", "updated comment user"))))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteComment() throws Exception {
        mockMvc.perform(delete("/posts/{postId}/comments/{commentId}", setPostId, setCommentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}