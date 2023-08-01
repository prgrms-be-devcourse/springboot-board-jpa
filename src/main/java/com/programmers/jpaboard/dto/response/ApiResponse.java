package com.programmers.jpaboard.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";

    private String status;
    private String message;
    private T data;

    private ApiResponse(String status) {
        this.status = status;
    }

    private ApiResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    private ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(SUCCESS_STATUS);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS_STATUS, data);
    }

    public static ApiResponse<Void> fail(String message) {
        return new ApiResponse<>(FAIL_STATUS, message);
    }

    public static <T> ApiResponse<T> fail(String message, T data) {
        return new ApiResponse<>(FAIL_STATUS, message, data);
    }
}
