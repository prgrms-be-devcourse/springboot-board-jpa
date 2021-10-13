package com.kdt.programmers.forum.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse<T> {
    private final T error;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime serverDateTime;

    public ErrorResponse(T error) {
        this.error = error;
        this.serverDateTime = LocalDateTime.now();
    }

    public static <T> ErrorResponse<T> response(T data) {
        return new ErrorResponse<>(data);
    }
}
