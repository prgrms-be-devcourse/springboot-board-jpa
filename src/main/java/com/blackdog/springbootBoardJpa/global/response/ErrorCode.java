package com.blackdog.springbootBoardJpa.global.response;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    //global
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G001", "Internal Server Error"),
    INVALID_METHOD_ERROR(HttpStatus.METHOD_NOT_ALLOWED, "G002", "Method Argument가 적절하지 않습니다."),

    //user
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "U001", "존재하지 않는 유저입니다."),

    //post
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "P001", "존재하지 않는 게시글입니다."),
    PERMISSION_DENIED(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "P002", "권한 없는 유저입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorCode(final HttpStatus httpStatus,
              final String code,
              final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}
