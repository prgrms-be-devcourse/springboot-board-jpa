package com.example.springbootboardjpa.controller;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;


@Getter
public class ApiResponse<T> extends ResponseEntity<T> {


    public ApiResponse(int statusCode, @RequestBody T data) {
        super(data,HttpStatus.valueOf(statusCode));
    }

    public ApiResponse(int statusCode,HttpHeaders httpHeaders, @RequestBody T data) {
        super(data,httpHeaders,HttpStatus.valueOf(statusCode));
    }
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, data);
    }

    public static <T> ApiResponse<T> ok(T data, String uri) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(uri));
        return new ApiResponse<>(200,httpHeaders, data);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T data) {
        return new ApiResponse<>(statusCode, data);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T data, String uri) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(uri));
        return new ApiResponse<>(statusCode,httpHeaders, data);
    }

}
