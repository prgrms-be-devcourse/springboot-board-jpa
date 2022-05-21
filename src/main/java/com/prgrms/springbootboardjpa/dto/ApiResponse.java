package com.prgrms.springbootboardjpa.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private int code;
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime timestamp;

    public ApiResponse(int code, T data) {
        this.code = code;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(201, data);
    }


    public static <T> ApiResponse<T> fail(int code, T data) {
        return new ApiResponse<>(code, data);
    }


}
