package com.example.board.dto.response;

import com.example.board.exception.ErrorResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private Boolean isSuccess;
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime datetime;

    private ApiResponse(Boolean isSuccess) {
        this.isSuccess = isSuccess;
        this.datetime = LocalDateTime.now();
    }

    private ApiResponse(Boolean isSuccess, T data) {
        this.isSuccess = isSuccess;
        this.data = data;
        this.datetime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data);
    }

    public static ApiResponse<ErrorResponse> fail(ErrorResponse data) {
        return new ApiResponse<>(false, data);
    }
}
