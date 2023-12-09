package com.programmers.boardjpa.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 User를 찾을 수 없습니다."),
    INVALID_AGE_RANGE(HttpStatus.BAD_REQUEST, "올바르지 않은 나이 범위입니다.");

    private final HttpStatus errorCode;
    private final String errorMessage;

    UserErrorCode(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
