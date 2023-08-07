package com.prgrms.boardjpa.global.exception;

import com.prgrms.boardjpa.global.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidDomainConditionException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidDomainConditionException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}