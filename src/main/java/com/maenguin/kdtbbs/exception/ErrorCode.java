package com.maenguin.kdtbbs.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    UNHANDLED(-1, "unhandled exception occurred"),
    USER_NOT_FOUND(100, "Can not find a user"),
    POST_NOT_FOUND(101, "Can not find a post");

    private final int code;

    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
