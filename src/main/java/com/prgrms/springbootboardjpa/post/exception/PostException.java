package com.prgrms.springbootboardjpa.post.exception;

public class PostException extends RuntimeException {

    public PostException() {
    }

    public PostException(String message) {
        super(message);
    }
}
