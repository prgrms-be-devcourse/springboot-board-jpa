package com.prgrms.springbootboardjpa.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private T data;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    private ApiResponse(T data) {
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data);
    }

    public static ErrorResponse error(String message) { return new ErrorResponse(message); }

    static class ErrorResponse {
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private final String error;

        private ErrorResponse(String error) {
            this.error = error;
        }
    }
}
