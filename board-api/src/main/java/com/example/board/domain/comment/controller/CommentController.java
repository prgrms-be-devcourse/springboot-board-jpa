package com.example.board.domain.comment.controller;

import com.example.board.domain.comment.dto.CommentCreateRequest;
import com.example.board.domain.comment.dto.CommentResponse;
import com.example.board.domain.comment.dto.CommentUpdateRequest;
import com.example.board.domain.comment.service.CommentService;
import com.example.board.global.security.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponse> createComment(
        @PathVariable("postId") Long postId,
        @Valid @RequestBody CommentCreateRequest commentCreateRequest
    ){
        Long currentUserId = SecurityUtil.getCurrentUserIdCheck();
        CommentResponse comment = commentService.createComment(postId, currentUserId, commentCreateRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(comment.getCommentId())
            .toUri();

        return ResponseEntity.created(location)
            .body(comment);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByPostId(@PathVariable(name = "postId") Long postId) {
        List<CommentResponse> comments = commentService.findAllCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
        @PathVariable(name = "commentId") Long commentId,
        @Valid @RequestBody CommentUpdateRequest request) {

        CommentResponse comment = commentService.updateComment(commentId, request);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteCommentByCommentId(@PathVariable Long commentId) {
        commentService.deleteCommentByCommentId(commentId);
        return ResponseEntity.noContent()
            .build();
    }
}
