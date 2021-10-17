package com.maenguin.kdtbbs.dto;

import com.maenguin.kdtbbs.exception.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@ToString(of = {"success", "data", "error"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private ApiError error;
    private LocalDateTime serverDateTime;

    private ApiResponse(boolean success, T data, ApiError error) {
        this.success = success;
        this.data = data;
        this.error = error;
        this.serverDateTime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(T response) {
        return new ApiResponse<>(true, response, null);
    }

    public static ApiResponse<?> error(int errorCode, String message) {
        return new ApiResponse<>(false, null, new ApiError(errorCode, message));
    }

    @Getter
    @ToString(of = {"errorCode", "message"})
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    protected static class ApiError {

        private int errorCode;
        private String message;

        public ApiError(int errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }
    }
}
