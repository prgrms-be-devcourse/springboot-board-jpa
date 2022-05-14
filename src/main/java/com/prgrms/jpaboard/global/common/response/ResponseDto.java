package com.prgrms.jpaboard.global.common.response;

import lombok.Getter;

@Getter
public class ResponseDto<T> {
    private int statusCode;
    private String message;
    private T result;

    public ResponseDto(int statusCode, String message, T result) {
        this.statusCode = statusCode;
        this.message = message;
        this.result = result;
    }
}
