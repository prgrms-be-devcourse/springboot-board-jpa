package com.prgrms.board.global.common;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseResponse<T> {

    private final int status;
    private final String message;

    @JsonInclude(NON_NULL)
    private T data;

    public static <T> BaseResponse<T> ok(SuccessMessage message, T data) {
        return new BaseResponse<>(OK.value(), message.getValue(), data);
    }

    public static <T> BaseResponse<T> created(SuccessMessage message, T data) {
        return new BaseResponse<>(CREATED.value(), message.getValue(), data);
    }

    public static BaseResponse<Object> error(ErrorCode error) {
        return new BaseResponse<>(error.getStatusCode(), error.getMessage());
    }

    public static BaseResponse<Object> error(HttpStatus status, String message) {
        return new BaseResponse<>(status.value(), message);
    }
}
