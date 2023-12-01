package com.programmers.boardjpa.post.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PostException extends RuntimeException {
    private final HttpStatus errorCode;

    public PostException(PostErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode.getErrorCode();
    }

    public PostException(PostErrorCode errorCode, Long id) {
        super(errorCode.getErrorMessage() + " : " + id);
        this.errorCode = errorCode.getErrorCode();
    }
}
