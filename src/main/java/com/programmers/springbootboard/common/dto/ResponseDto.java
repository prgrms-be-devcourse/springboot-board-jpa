package com.programmers.springbootboard.common.dto;

import java.util.Collection;

public class ResponseDto<T> {
    private int status;
    private String message;
    private T data;
    private T link;

    public ResponseDto(ResponseMessage message) {
        this.status = message.status().value();
        this.message = message.name();
    }

    public ResponseDto(ResponseMessage message, T data) {
        this(message);
        this.data = data;
    }

    public ResponseDto(ResponseMessage message, T data, T link) {
        this(message, data);
        this.link = link;
    }

    public static ResponseDto of(ResponseMessage message) {
        return new ResponseDto(message);
    }

    public static <T> ResponseDto of(ResponseMessage message, T data) {
        return new ResponseDto(message, data);
    }

    public static <T> ResponseDto of(ResponseMessage message, T data, T link) {
        return new ResponseDto(message, data, link);
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

    public T getLink() {
        return link;
    }
}
