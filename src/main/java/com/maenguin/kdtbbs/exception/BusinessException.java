package com.maenguin.kdtbbs.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage() + message);
        this.errorCode = errorCode;
    }
}
