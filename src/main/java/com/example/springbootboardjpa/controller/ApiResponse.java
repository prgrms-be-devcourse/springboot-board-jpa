package com.example.springbootboardjpa.controller;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;


@Getter
public class ApiResponse<T> extends ResponseEntity<T> {

    public ApiResponse(T data, int statusCode) {
        super(data, HttpStatus.valueOf(statusCode));
    }

    public ApiResponse(T data, HttpHeaders httpHeaders, int statusCode) {
        super(data, httpHeaders, HttpStatus.valueOf(statusCode));
    }


    public static <T> ApiResponse<T> ok(T data) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ApiResponse<>(data, httpHeaders,200);
    }

    public static <T> ApiResponse<T> ok(T data, String uri) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setLocation(URI.create(uri));
        return new ApiResponse<>(data, httpHeaders, 200);
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
