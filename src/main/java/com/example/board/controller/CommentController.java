package com.example.board.controller;

import com.example.board.domain.service.CommentService;
import com.example.board.dto.comment.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<Long> createComment(@Valid @RequestBody CommentRequestDto requestDto) {
        Long commentId = commentService.createComment(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentId);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
