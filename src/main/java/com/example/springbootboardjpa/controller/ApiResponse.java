package com.example.springbootboardjpa.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class ApiResponse<T> extends ResponseEntity<T> {

    private ApiResponse(T data, int statusCode) {
        super(data, HttpStatus.valueOf(statusCode));
    }

    private ApiResponse(T data, HttpHeaders httpHeaders, int statusCode) {
        super(data, httpHeaders, HttpStatus.valueOf(statusCode));
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data, 200);
    }

    public static <T> ApiResponse<T> ok(T data, String uri) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(uri));
        return new ApiResponse<>(data, httpHeaders, 200);
    }

    public static <T> ApiResponse<T> created(T data, String uri) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(uri));
        return new ApiResponse<>(data, httpHeaders, 201);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T data) {
        return new ApiResponse<>(data, statusCode);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T data, String uri) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(uri));
        return new ApiResponse<>(data, httpHeaders, statusCode);
    }

}
