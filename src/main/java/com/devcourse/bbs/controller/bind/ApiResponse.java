package com.devcourse.bbs.controller.bind;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponse<T> {
    private final boolean success;
    private final T result;
    private final String error;

    public static <U> ApiResponse<U> success(U result) {
        return new ApiResponse<>(true, result, "");
    }

    public static <U> ApiResponse<U> fail(U result, String message) {
        return new ApiResponse<>(false, result, message);
    }

    public static <U> ApiResponse<U> fail(String message) {
        return fail(null, message);
    }
}
