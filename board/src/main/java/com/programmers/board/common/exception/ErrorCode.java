package com.programmers.board.common.exception;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "서버에 문제가 생겼습니다."),

    METHOD_NOT_ALLOWED(405,"적잘하지 않은 HTTP 메소드입니다."),
    INVALID_INPUT_VALUE(400, "적잘하지 않은 요청 값입니다."),
    INVALID_TYPE_VALUE(400, "요청 값의 타입이 잘못되었습니다."),
    POST_NOT_FOUND(404, "해당 포스트가 존재하지 않습니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message){
        this.status = status;
        this.message = message;
    }

    public static ErrorCode fromMessage(String message){
        return Arrays.stream(ErrorCode.values())
                .filter(errorCode -> errorCode.getMessage().equals(message))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("없는 메시지"));
    }
}
