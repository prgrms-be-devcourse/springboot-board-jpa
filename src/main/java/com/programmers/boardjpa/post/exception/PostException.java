package com.programmers.boardjpa.post.exception;

import lombok.Getter;

@Getter
public class PostException extends RuntimeException {
    private final HttpStatus errorCode;

    public PostException(PostErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public PostException(PostErrorCode errorCode, Long id) {
        super(errorCode.getErrorMessage() + " : " + id);
        this.errorCode = errorCode.getErrorCode();
    }
}
