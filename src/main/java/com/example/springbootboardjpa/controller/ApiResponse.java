package com.example.springbootboardjpa.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


@Getter
@Setter
public class ApiResponse<T> extends ResponseEntity<T> {

    HttpHeaders httpHeaders = new HttpHeaders();
    public ApiResponse(int statusCode,@RequestBody T data) {
        super(data,HttpStatus.valueOf(statusCode));

    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, data);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T data) {
        return new ApiResponse<>(statusCode, data);
    }
}
