package com.prgrms.boardjpa.global.exception;

import com.prgrms.boardjpa.global.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessServiceException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessServiceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}