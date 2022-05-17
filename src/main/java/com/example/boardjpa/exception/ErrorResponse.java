package com.example.boardjpa.exception;

public class ErrorResponse {
    private final Integer status;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
