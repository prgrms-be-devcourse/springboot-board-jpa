package com.jpaboard;


public class ApiResponse<T> {

    private final int statusCode;
    private final T data;

    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, data);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T errorData) {
        return new ApiResponse<>(statusCode, errorData);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getData() {
        return data;
    }
}
