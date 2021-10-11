package com.programmers.springboard.config;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
    private HttpStatus status;
    private T data;

    public ApiResponse(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<T>(HttpStatus.OK, data);
    }

}
