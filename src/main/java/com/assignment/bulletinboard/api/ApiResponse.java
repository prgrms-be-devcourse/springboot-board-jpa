package com.assignment.bulletinboard.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {

    private T data;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    public ApiResponse(T data) {
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data);
    }
}
