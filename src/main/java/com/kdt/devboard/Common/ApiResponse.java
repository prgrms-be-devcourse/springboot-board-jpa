package com.kdt.devboard.Common;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private int statusCode;

    private T data;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    @Builder
    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
        this.serverDatetime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, data);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T data) {
        return new ApiResponse<>(statusCode, data);
    }

}
