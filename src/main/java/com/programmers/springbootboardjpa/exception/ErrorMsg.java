package com.programmers.springbootboardjpa.exception;

import lombok.Getter;

@Getter
public enum ErrorMsg {
    USER_NOT_FOUND(100, "유저가 존재하지 않습니다."),
    POST_NOT_FOUND(101, "포스트가 존재하지 않습니다.");

    private final int code;
    private final String message;

    ErrorMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
