package org.prgms.board.comment.controller;

import org.prgms.board.comment.dto.CommentRequest;
import org.prgms.board.comment.service.CommentService;
import org.prgms.board.common.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<Object> addComment(@PathVariable Long userId, @PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, commentService.addComment(userId, postId, commentRequest));
    }

    @PutMapping("/{userId}/{commentId}")
    public ResponseEntity<Object> modifyComment(@PathVariable Long userId, @PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        return ResponseHandler.generateResponse("Successfully updated data!", HttpStatus.OK, commentService.modifyComment(userId, commentId, commentRequest));
    }

    @DeleteMapping("/{userId}/{commentId}")
    public ResponseEntity<Object> removeComment(@PathVariable Long userId, @PathVariable Long commentId) {
        commentService.removeComment(userId, commentId);
        return ResponseHandler.generateResponse("Successfully removed data!", HttpStatus.OK, null);
    }
}
