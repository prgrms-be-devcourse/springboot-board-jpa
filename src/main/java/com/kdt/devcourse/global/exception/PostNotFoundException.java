package com.kdt.devcourse.global.exception;

public class PostNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public PostNotFoundException(ErrorCode errorCode, Long id) {
        super(errorCode.getMessage() + id);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode.name();
    }
}
