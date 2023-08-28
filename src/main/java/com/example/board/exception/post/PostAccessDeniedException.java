package com.example.board.exception.post;

public class PostAccessDeniedException extends RuntimeException {
    public PostAccessDeniedException(String message) {
        super(message);
    }
}
