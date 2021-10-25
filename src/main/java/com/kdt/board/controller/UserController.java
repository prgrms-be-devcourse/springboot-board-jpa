package com.kdt.board.controller;

import com.kdt.board.ApiResponse;
import com.kdt.board.domain.User;
import com.kdt.board.dto.comment.CommentResponse;
import com.kdt.board.dto.post.PostResponse;
import com.kdt.board.service.CommentService;
import com.kdt.board.service.PostService;
import com.kdt.board.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    public UserController(PostService postService, CommentService commentService, UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/{userId}/posts")
    public ApiResponse<List<PostResponse>> getPostsByUser(
            @PathVariable Long userId
    ) {
        User user = userService.findOneUser(userId).get();
        return ApiResponse.ok(
                postService.findAllByUser(user).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{userId}/comments")
    public ApiResponse<List<CommentResponse>> getCommentsByUser(
            @PathVariable Long userId
    ) {
        User user = userService.findOneUser(userId).get();
        return ApiResponse.ok(
                commentService.findAllByUser(user).stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList()));
    }
}
