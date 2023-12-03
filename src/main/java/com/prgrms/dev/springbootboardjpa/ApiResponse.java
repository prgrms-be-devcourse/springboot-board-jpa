package com.prgrms.dev.springbootboardjpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    // custome 하는 이유?
    // responseEntity
    // statusCode 를 내부적으로 약속하여 내려줄 때 의미 있을
    // 제네릭을 왜 사용하는지 (제네릭 굉부...)
    private static final int SUCCESS = 200;

    private int statusCode;
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime responseTime; // LocalDataTime -> Json 요청과 응답으로 어떻게 처리해야 하는지. , 사용 기준 ...

    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
        this.responseTime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(SUCCESS, data);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T data) {
        return new ApiResponse<>(statusCode, data);
    }

}
