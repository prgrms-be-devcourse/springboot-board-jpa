package com.prgms.springbootboardjpa.exception;

public enum ExceptionMessage {
    USER_NOT_FOUND("존재하지 않은 사용자입니다."),
    POST_NOT_FOUND("존재하지 않는 게시글입니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}