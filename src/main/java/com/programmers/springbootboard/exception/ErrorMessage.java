package com.programmers.springbootboard.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum ErrorMessage {
    INVALID_MEMBER_NAME(HttpStatus.BAD_REQUEST, "이름 형식이 맞지 않습니다."),
    INVALID_MEMBER_EMAIL(HttpStatus.BAD_REQUEST, "이메일 형식이 맞지 않습니다."),
    INVALID_MEMBER_AGE(HttpStatus.BAD_REQUEST, "나이 형식이 맞지 않습니다."),
    INVALID_MEMBER_HOBBY(HttpStatus.BAD_REQUEST, "취미 형식이 맞지 않습니다."),
    NOT_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "정의되지 않은 서버 에러");

    private final HttpStatus status;
    private final String message;

    ErrorMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus status() {
        return status;
    }

    public String message() {
        return message;
    }


    public static ErrorMessage of(String errorMessage) {
        return Arrays.stream(values())
                .filter(e -> e.message.equals(errorMessage))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Non Existent Exception"));
    }
}
