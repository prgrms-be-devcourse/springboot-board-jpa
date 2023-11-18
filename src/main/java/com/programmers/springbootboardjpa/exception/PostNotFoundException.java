package com.programmers.springbootboardjpa.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super(ErrorMsg.POST_NOT_FOUND.getMessage());
    }
}
