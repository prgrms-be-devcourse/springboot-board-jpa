package com.example.springbootboardjpa.controller;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


@Getter
public class ApiResponse<T> extends ResponseEntity<T> {

    public ApiResponse(int statusCode, @RequestBody T data) {
        super(data, HttpStatus.valueOf(statusCode));

    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, data);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T data) {
        return new ApiResponse<>(statusCode, data);
    }
}
