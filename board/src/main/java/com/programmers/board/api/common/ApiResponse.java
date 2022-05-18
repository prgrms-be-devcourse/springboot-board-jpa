package com.programmers.board.api.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private int statusCode;

    private T body;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDateTime;

    private ApiResponse(int statusCode, T body) {
        this.statusCode = statusCode;
        this.body = body;
        this.serverDateTime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(T body){
        return new ApiResponse<>(200, body);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T body){
        return new ApiResponse<>(statusCode, body);
    }
}
