package com.prgrms.work.controller.dto;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

    private T data;
    private HttpStatus status;

    public ApiResponse(T data, HttpStatus status) {
        this.data = data;
        this.status = status;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<T>(data, HttpStatus.OK);
    }

    public static <T> ApiResponse<T> fail(T data, HttpStatus status) {
        return new ApiResponse<T>(data, status);
    }

    public static <T> ApiResponse<T> create(T data) {
        return new ApiResponse<>(data, HttpStatus.CREATED);
    }

    public T getData() {
        return this.data;
    }

    public HttpStatus getStatusCode() {
        return this.status;
    }

}
