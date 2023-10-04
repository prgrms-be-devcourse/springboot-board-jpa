package com.programmers.springbootboardjpa.global;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.programmers.springbootboardjpa.global.exception.ErrorResponse;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final Integer statusCode;
    private final T data;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime serverDatetime;

    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, data);
    }

    public static ApiResponse<ErrorResponse> fail(ErrorResponse errorResponse) {
        return new ApiResponse<>(errorResponse.getStatusCode(), errorResponse);
    }

}
