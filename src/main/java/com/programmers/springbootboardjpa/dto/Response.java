package com.programmers.springbootboardjpa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Response<T> {
    private int statusCode;

    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    private T data;

    public Response(int statusCode, T data, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.serverDatetime = LocalDateTime.now();
        this.data = data;
    }

    public static <T> Response<T> ok(T data, String message) {
        return new Response<>(200, data, message);
    }

    public static <T> Response<T> fail(int statusCode, T errData, String message) {
        return new Response<>(statusCode, errData, message);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getServerDatetime() {
        return serverDatetime;
    }

    public T getData() {
        return data;
    }
}
