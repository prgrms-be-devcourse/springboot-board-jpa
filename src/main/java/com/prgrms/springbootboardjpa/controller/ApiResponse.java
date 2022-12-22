package com.prgrms.springbootboardjpa.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private T data;
    private String message = "NO MESSAGE";

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    private ApiResponse(T data) {
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    private ApiResponse(String message) {
        this.message = message;
        this.serverDatetime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> of(T data, String message) {
        return new ApiResponse<>(data, message, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> of(String message) {
        return new ApiResponse<>(message);
    }
}
