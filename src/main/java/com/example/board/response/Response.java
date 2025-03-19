package com.example.board.response;
import com.example.board.exception.BaseException;
import java.util.List;

public record Response<T>(T data, int code, String isSuccess) {
    public static <T> Response<T> success(T data) {
        return new Response<>(data, 200, "ok");
    }

    public static Response<String> fail(BaseException e) {
        return new Response<>(e.getMessage(), e.getCode(), "fail");
    }

    public static Response<String> fail(Exception e) {
        return new Response<>(e.getMessage(), 500, "fail");
    }

    public static Response<List<String>> fail(List<String> bindingErrors) {
        return new Response<>(bindingErrors, 400, "fail");
    }
}
