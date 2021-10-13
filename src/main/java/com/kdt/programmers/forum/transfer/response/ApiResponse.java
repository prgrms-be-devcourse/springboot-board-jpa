package com.kdt.programmers.forum.transfer.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {
    private final T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime serverDateTime;

    public ApiResponse(T data) {
        this.data = data;
        this.serverDateTime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> response(T data) {
        return new ApiResponse<>(data);
    }
}
