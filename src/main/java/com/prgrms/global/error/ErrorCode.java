package com.prgrms.global.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터 값을 확인해주세요."),

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시물 입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저 입니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "로그인에 실패하였습니다."),
    AUTHENCTICATION_FAILED(HttpStatus.UNAUTHORIZED, "로그인 이후 이용이 가능합니다"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다.");

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
