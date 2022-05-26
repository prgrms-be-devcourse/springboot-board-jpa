package com.prgrms.boardjpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Response<T> {
    private int statusCode;
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;
    private String message;

    public Response(int statusCode, T data, String message) {
        this.statusCode = statusCode;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
        this.message = message;
    }

    public static <T> Response<T> ok(T data, String message) {
        return new Response<>(200, data, message);
    }

    public static <T> Response<T> fail(int statusCode, T errorData, String message) {
        return new Response<>(statusCode, errorData, message);
    }
}
