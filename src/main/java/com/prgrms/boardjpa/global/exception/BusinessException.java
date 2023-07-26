package com.prgrms.boardjpa.global.exception;

import com.prgrms.boardjpa.global.ErrorCode;

public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;

    }
}