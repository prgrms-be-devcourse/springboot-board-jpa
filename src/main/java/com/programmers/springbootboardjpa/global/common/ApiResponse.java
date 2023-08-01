package com.programmers.springbootboardjpa.global.common;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(HttpStatus httpStatus, T data) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK, data);
    }

    public static <T> ApiResponse<T> fail(HttpStatus httpStatus, T errData) {
        return new ApiResponse<>(httpStatus, errData);
    }
}
