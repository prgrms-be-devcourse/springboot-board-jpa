package com.prgrms.board.controller;

import com.prgrms.board.dto.comment.CommentResponse;
import com.prgrms.board.dto.comment.CommentSaveRequest;
import com.prgrms.board.dto.comment.CommentUpdateRequest;
import com.prgrms.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @RequestBody @Valid CommentSaveRequest saveRequest
    ) {
        CommentResponse commentResponse = commentService.createComment(saveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(
            @RequestParam Long postId
    ) {
        List<CommentResponse> commentResponse = commentService.findCommentsByPostId(postId);
        return ResponseEntity.ok(commentResponse);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @RequestBody @Valid CommentUpdateRequest updateRequest,
            @PathVariable Long commentId
    ) {
        CommentResponse commentResponse = commentService.updateComment(updateRequest, commentId);
        return ResponseEntity.ok(commentResponse);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}