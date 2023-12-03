package com.example.board.global.exception;
import lombok.Getter;

import static com.example.board.global.exception.ErrorCode.defaultError;

@Getter
public class CustomException extends RuntimeException {

    private ErrorCode errorCode = defaultError();

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
