package com.blackdog.springbootBoardJpa.global.exception;

import com.blackdog.springbootBoardJpa.global.response.ErrorCode;

public class UserNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
