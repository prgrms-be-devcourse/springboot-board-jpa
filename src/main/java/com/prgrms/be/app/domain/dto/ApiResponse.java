package com.prgrms.be.app.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {
    private final int statusCode;
    private final T data;
    private final String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime serverDateTime;

    private ApiResponse(int statusCode, T data, String message) {
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
        this.serverDateTime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<T>(200, data, message);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T data, String message) {
        return new ApiResponse<>(statusCode, data, message);
    }
}
