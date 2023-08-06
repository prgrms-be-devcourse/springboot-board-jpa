package com.kdt.devcourse.global.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode.name();
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}
