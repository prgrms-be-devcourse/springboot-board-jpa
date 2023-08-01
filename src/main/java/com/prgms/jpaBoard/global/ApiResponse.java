package com.prgms.jpaBoard.global;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        int statusCode,
        T data,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime serverDateTime
) {

    public ApiResponse(int statusCode, T data) {
        this(statusCode, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, data);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T errData) {
        return new ApiResponse<>(statusCode, errData);
    }

    public static <T> ApiResponse<T> success(int statusCode, T data){
        return new ApiResponse<>(statusCode, data);
    }
}
