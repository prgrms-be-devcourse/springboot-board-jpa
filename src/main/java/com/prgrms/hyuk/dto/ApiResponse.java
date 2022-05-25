package com.prgrms.hyuk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.net.URI;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

    private int statusCode;
    private T data;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), data);
    }

    public static ApiResponse<URI> created(URI location) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), location);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T errData) {
        return new ApiResponse<>(statusCode, errData);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getData() {
        return data;
    }

    public LocalDateTime getServerDatetime() {
        return serverDatetime;
    }
}
