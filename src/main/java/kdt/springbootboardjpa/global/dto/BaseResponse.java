package kdt.springbootboardjpa.global.dto;

import org.springframework.http.HttpStatus;

public record BaseResponse<T>(HttpStatus status, T response, ApiError error) {

    public static <T> BaseResponse<T> success(HttpStatus status, T response) {
        return new BaseResponse<>(status, response, null);
    }

    public static <T> BaseResponse<T> error(HttpStatus status, ApiError error) {
        return new BaseResponse<>(status, null, error);
    }
}

