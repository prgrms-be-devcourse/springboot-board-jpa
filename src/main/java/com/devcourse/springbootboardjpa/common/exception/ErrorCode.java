package com.devcourse.springbootboardjpa.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("존재하지 않는 사용자입니다.", 404),
    POST_NOT_FOUND("존재하지 않는 게시글입니다.", 404);

    private final String message;
    private final int status;

    ErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
