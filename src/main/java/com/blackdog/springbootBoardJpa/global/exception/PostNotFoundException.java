package com.blackdog.springbootBoardJpa.global.exception;

import com.blackdog.springbootBoardJpa.global.response.ErrorCode;

public class PostNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public PostNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
