package com.example.demo.exception;

public enum ErrorStatus {

    //Common
    INTERNAL_SERVER_ERROR(500, "서버 에러"),

    //Post
    ENTITY_NOT_FOUND(204, "엔티티를 찾을 수 없음");

    private final int status;
    private final String message;

    ErrorStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}


