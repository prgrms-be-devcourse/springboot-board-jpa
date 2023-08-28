package com.example.board.exception.comment;

public class NotFoundCommentException extends RuntimeException{
    public NotFoundCommentException(String message) {
        super(message);
    }
}
