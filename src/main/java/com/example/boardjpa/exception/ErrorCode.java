package com.example.boardjpa.exception;

public enum ErrorCode {
    USER_NOT_FOUND(400, "USER IS NOT FOUND")
    , POST_NOT_FOUND(400, "POST IS NOT FOUND")
    , API_NOT_FOUND(400, "API IS NOT FOUND")
    , INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR");

    private final Integer status;
    private final String message;

    ErrorCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
