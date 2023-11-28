package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResult<T> {
    private String resultCode;
    private String msg;
    private T data;

    public static <T> ApiResult<T> of(String resultCode, String msg, T data) {
        return new ApiResult<>(resultCode, msg, data);
    }

    public static <T> ApiResult<T> of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

    public static <T> ApiResult<T> successOf(T data) {
        return of("S01", "성공", data);
    }

    public static <T> ApiResult<T> successOf() {
        return of("S01", "성공", null);
    }

    public static <T> ApiResult<T> failOf(T data) {
        return of("F01", "실패", data);
    }

    public boolean isSuccess() {
        return resultCode.startsWith("S01");
    }

    public boolean isFail() {
        return !isSuccess();
    }
}
