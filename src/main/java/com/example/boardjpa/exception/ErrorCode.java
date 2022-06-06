package com.example.boardjpa.exception;

public enum ErrorCode {
    USER_NOT_FOUND(400, "USER IS NOT FOUND")
    , POST_NOT_FOUND(400, "POST IS NOT FOUND")
    , FIELD_NULL(400, "FIELD CANT BE NULL")
    , FIELD_BLANK(400, "FIELD CANT BE BLANK")
    , AGE_OUT_OF_RANGE(400, "AGE VALUE IS OUT OF RANGE")
    , API_NOT_FOUND(404, "API IS NOT FOUND")
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
