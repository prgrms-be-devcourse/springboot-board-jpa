package com.example.board.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final Boolean isSuccess;
    private final Integer code;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, ResponseStatus.OK.getCode(), ResponseStatus.OK.getMessage(), null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, ResponseStatus.OK.getCode(), ResponseStatus.OK.getMessage(), data);
    }

    public static <T> ApiResponse<T> success(ResponseStatus status, T data) {
        return new ApiResponse<>(true, status.getCode(), status.getMessage(), data);
    }

    public static <T> ApiResponse<T> fail(ResponseStatus status, T data) {
        return new ApiResponse<>(false, status.getCode(), status.getMessage(), data);
    }
}
