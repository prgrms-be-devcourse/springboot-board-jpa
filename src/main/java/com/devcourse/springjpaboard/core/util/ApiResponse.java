package com.devcourse.springjpaboard.core.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {

    private int statusCode;

    private T data;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/seoul")
    private LocalDateTime serverDateTime;

    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
        this.serverDateTime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(HttpStatus status, T data) {
        return new ApiResponse<>(status.value(), data);
    }

    public static <T> ApiResponse<T> fail(HttpStatus status, T data) {
        return new ApiResponse<>(status.value(), data);
    }
}
