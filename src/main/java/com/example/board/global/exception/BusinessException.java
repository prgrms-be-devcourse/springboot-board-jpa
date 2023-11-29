package com.example.board.global.exception;
import lombok.Getter;

import static com.example.board.global.exception.ErrorCode.defaultError;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode = defaultError();

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
