package com.example.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private int statusCode;
    private T data;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime datetime;

    public ApiResponse(int statusCode) {
        this.statusCode = statusCode;
        this.datetime = LocalDateTime.now();
    }

    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
        this.datetime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(HttpStatus status) {
        return new ApiResponse<>(status.value());
    }

    public static <T> ApiResponse<T> success(HttpStatus status, T data) {
        return new ApiResponse<>(status.value(), data);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T errData) {
        return new ApiResponse<>(statusCode, errData);
    }
}
