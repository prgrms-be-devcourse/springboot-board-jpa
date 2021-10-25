package com.kdt.board.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.board.domain.Comment;
import com.kdt.board.domain.Post;
import com.kdt.board.domain.User;
import com.kdt.board.dto.comment.CommentRequest;
import com.kdt.board.dto.comment.CommentResponse;
import com.kdt.board.dto.post.PostRequest;
import com.kdt.board.dto.user.UserRequest;
import com.kdt.board.repository.CommentRepository;
import com.kdt.board.repository.PostRepository;
import com.kdt.board.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class CommentServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    private static UserRequest postUserRequest;
    private static UserRequest commentUserRequest;
    private static PostRequest postRequest;
    private static CommentRequest commentRequest;
    private static Long setPostId;
    private static Long setCommentId;
    private static Long setPostUserId;
    private static Long setCommentUserId;

    @BeforeEach
    void createPost() {
        postUserRequest = new UserRequest("set post user");
        postRequest = new PostRequest("set title", "set content", postUserRequest.getName());
        commentUserRequest = new UserRequest("set comment user");
        commentRequest = new CommentRequest("set comment content", commentUserRequest.getName());
        setPostId = postService.createPost(postRequest);
        setCommentId = commentService.createComment(setPostId, commentRequest);
        setPostUserId = postService.findOnePost(setPostId).get().getUser().getId();
        setCommentUserId = commentService.findOneComment(setCommentId).get().getUser().getId();
    }

    // question : 왜 postRepository 먼저 teardown하면 오류나지?
    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findOneComment() {
        // Given
        Long commentId = setCommentId;
        // When
        Comment oneComment = commentService.findOneComment(commentId).get();
        // Then
        assertThat(oneComment.getId()).isEqualTo(setCommentId);
    }

    @Test
    void findAllCommentsByUser() {
        // Given
        User user = userService.findOneUser(setCommentUserId).get();
        // When
        List<Comment> comments = commentService.findAllByUser(user);
        // Then
        assertThat(comments.size()).isEqualTo(1);
    }

    @Test
    void updateComment() {
        // Given
        Long commentId = setCommentId;
        // When
        CommentResponse commentResponse = commentService.updateComment(commentId, new CommentRequest("updated comment content", "updated comment user"));
        // Then
        assertThat(commentId).isEqualTo(commentResponse.getCommentId());
    }

    @Test
    void deleteComment() {
        // Given
        Long commentId = setCommentId;
        // When
        commentService.deleteComment(setPostId, commentId);
        // Then
        Post post = postService.findOnePost(setPostId).get();
        assertThat(post.getComments().size()).isEqualTo(0);
    }
}