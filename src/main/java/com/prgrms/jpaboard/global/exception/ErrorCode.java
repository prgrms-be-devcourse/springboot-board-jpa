package com.prgrms.jpaboard.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "wrong input"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "can't find a user"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "can't find a post");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
