package com.example.board.exception.post;

public class NotFoundPostException extends RuntimeException {
    public NotFoundPostException(String message) {
        super(message);
    }
}
