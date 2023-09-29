package com.blackdog.springbootBoardJpa.global.exception;

import com.blackdog.springbootBoardJpa.global.response.ErrorCode;

public class PermissionDeniedException extends RuntimeException {
    private final ErrorCode errorCode;

    public PermissionDeniedException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
