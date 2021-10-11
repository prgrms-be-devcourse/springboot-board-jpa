package org.prgms.board.comment.controller;

import org.prgms.board.comment.dto.CommentRequest;
import org.prgms.board.comment.service.CommentService;
import org.prgms.board.common.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{userId}/{postId}")
    public ApiResponse<Long> addComment(@PathVariable Long userId, @PathVariable Long postId, @RequestBody @Valid CommentRequest commentRequest) {
        return ApiResponse.toResponse(commentService.addComment(userId, postId, commentRequest));
    }

    @PutMapping("/{userId}/{commentId}")
    public ApiResponse<Long> modifyComment(@PathVariable Long userId, @PathVariable Long commentId, @RequestBody @Valid CommentRequest commentRequest) {
        return ApiResponse.toResponse(commentService.modifyComment(userId, commentId, commentRequest));
    }

    @DeleteMapping("/{userId}/{commentId}")
    public ApiResponse<Integer> removeComment(@PathVariable Long userId, @PathVariable Long commentId) {
        commentService.removeComment(userId, commentId);
        return ApiResponse.toResponse(1);
    }
}
