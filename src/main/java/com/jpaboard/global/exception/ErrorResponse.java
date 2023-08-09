package com.jpaboard.global.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;
    private final List<FieldErrorInfo> errors;

    private ErrorResponse(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = new ArrayList<>();
    }

    private ErrorResponse(int status, String code, String message, List<FieldErrorInfo> errors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public static ErrorResponse create(ErrorCodeWithDetail detail) {
        return new ErrorResponse(detail.getStatus(),
                detail.getCode(),
                detail.getMessage());
    }

    public static ErrorResponse create(ErrorCodeWithDetail detail, BindingResult bindingResult) {
        return new ErrorResponse(detail.getStatus(),
                detail.getCode(),
                detail.getMessage(),
                FieldErrorInfo.create(bindingResult));
    }

}
