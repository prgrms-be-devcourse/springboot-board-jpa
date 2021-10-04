package com.programmers.springbootboard.common.dto;

public class ResponseDto<T> {
    private int status;
    private String message;
    private T data;

    public ResponseDto(ResponseMessage message) {
        this.status = message.status().value();
        this.message = message.name();
    }

    public ResponseDto(ResponseMessage message, T data) {
        this.status = message.status().value();
        this.message = message.name();
        this.data = data;
    }

    public static ResponseDto of(ResponseMessage message) {
        return new ResponseDto(message);
    }

    public static <T> ResponseDto of(ResponseMessage message, T data) {
        return new ResponseDto(message, data);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
