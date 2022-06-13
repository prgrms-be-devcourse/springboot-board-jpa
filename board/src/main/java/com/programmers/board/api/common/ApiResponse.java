package com.programmers.board.api.common;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private int statusCode;

    private T body;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDateTime;

    private ApiResponse(){}

    private ApiResponse(int statusCode, T body) {
        this.statusCode = statusCode;
        this.body = body;
        this.serverDateTime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(T body){
        return new ApiResponse<>(200, body);
    }

    public static <T> ApiResponse<T> created(T body){return new ApiResponse<>(201, body);}

    //Getter
    public int getStatusCode() {
        return statusCode;
    }

    public T getBody() {
        return body;
    }

    public LocalDateTime getServerDateTime() {
        return serverDateTime;
    }
}
