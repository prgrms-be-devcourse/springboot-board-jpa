package com.example.boardjpa.exception.custom;

import com.example.boardjpa.exception.ErrorCode;

public class RecordNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public RecordNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
