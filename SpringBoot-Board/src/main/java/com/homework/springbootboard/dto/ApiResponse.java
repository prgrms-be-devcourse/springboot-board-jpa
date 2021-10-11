package com.homework.springbootboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.homework.springbootboard.exception.ErrorStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {
    private ErrorStatus errorStatus;
    private int code;
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDateTime;

    public ApiResponse(ErrorStatus errorStatus, int code, T data) {
        this.errorStatus = errorStatus;
        this.code = code;
        this.data = data;
        this.serverDateTime = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this.code = 200;
        this.data = data;
        this.serverDateTime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> fail(ErrorStatus errorStatus, int code, T data) {
        return new ApiResponse<>(errorStatus, code, data);
    }
}
