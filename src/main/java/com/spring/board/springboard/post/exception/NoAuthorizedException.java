package com.spring.board.springboard.post.exception;

public class NoAuthorizedException extends RuntimeException{
    public NoAuthorizedException(String message) {
        super(message);
    }
}
