package com.example.board.response;

import com.example.exception.BaseException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Response<T> {

    private T data;
    private int code;
    private String isSuccess;

    public static <T> Response<T> success(T data) {
        return new Response<>(data, 200, "ok");
    }

    public static <T> Response<T> success() {
        return new Response<>(null, 200, "ok");
    }

    public static Response<String> fail(BaseException e) {
        return new Response<>(e.getMessage(), e.getCode(), "fail");
    }

    public static Response<String> fail(Exception e) {
        return new Response<>(e.getMessage(), 500, "fail");
    }

}
