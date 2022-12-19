package com.prgrms.be.app.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {
    private final T data;
    private final String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime serverDateTime;

    private ApiResponse(T data, String message) {
        this.data = data;
        this.serverDateTime = LocalDateTime.now();
        this.message = message;
    }

    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<T>(data, message);
    }

    public static <T> ApiResponse<T> fail(T data, String message) {
        return new ApiResponse<>(data, message);
    }
}
