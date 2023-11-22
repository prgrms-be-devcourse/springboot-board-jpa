package com.example.board.exception;

public class CustomException extends GeneralException {
    public CustomException(ErrorCode errorCode) {
        super(errorCode);
    }
}
