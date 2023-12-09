package com.example.board.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final Boolean isSuccess;
    private final Integer code;
    private final String message;
    private final T result;

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, ResponseStatus.OK.getCode(), ResponseStatus.OK.getMessage(), null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, ResponseStatus.OK.getCode(), ResponseStatus.OK.getMessage(), data);
    }

    public static <T> ApiResponse<T> success(ResponseStatus status, T result) {
        return new ApiResponse<>(true, status.getCode(), status.getMessage(), result);
    }

    public static <T> ApiResponse<T> fail(ResponseStatus status) {
        return new ApiResponse<>(false, status.getCode(), status.getMessage(), null);
    }

    public static <T> ApiResponse<T> fail(ResponseStatus status, T result) {
        return new ApiResponse<>(false, status.getCode(), status.getMessage(), result);
    }
}
