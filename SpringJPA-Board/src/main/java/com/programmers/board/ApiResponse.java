package com.programmers.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> { // 공통 형식의 api 응답을 만들고 싶어서 생성한 클래스
    private int statusCode;
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(T data) { // ok 응답을 할 수 있는 static 메소드
        return new ApiResponse<>(200, data);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T errData) { // fail 응답을 할 수 있는 static 메소드
        return new ApiResponse<>(statusCode, errData);
    }

}
