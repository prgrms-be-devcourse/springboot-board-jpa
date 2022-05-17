package com.example.boardjpa.exception.custom;

import com.example.boardjpa.exception.ErrorCode;

public class FieldBlankException extends RuntimeException {
    private final ErrorCode errorCode;

    public FieldBlankException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
