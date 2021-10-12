package com.kdt.programmers.forum.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {
    private final HttpStatus status;
    private final T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime serverDateTime;

    public ApiResponse(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
        this.serverDateTime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK, data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(HttpStatus.CREATED, data);
    }

    public static <T> ApiResponse<T> badRequest(T data) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, data);
    }

    public static <T> ApiResponse<T> notFound(T data) {
        return new ApiResponse<>(HttpStatus.NOT_FOUND, data);
    }
}
