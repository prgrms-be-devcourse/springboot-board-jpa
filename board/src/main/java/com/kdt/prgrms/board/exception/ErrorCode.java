package com.kdt.prgrms.board.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


public enum ErrorCode {

    INVALID_INPUT_REQUEST(BAD_REQUEST, "입력 정보가 유효하지 않습니다."),

    REQUEST_NOT_FOUND(NOT_FOUND, "해당 요청을 찾을 수 없습니다."),

    SERVER_ERROR(INTERNAL_SERVER_ERROR, "요청 수행을 실패했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {

        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {

        return status;
    }

    public String getMessage() {

        return message;
    }
}
