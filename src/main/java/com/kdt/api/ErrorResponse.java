package com.kdt.api;

import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class ErrorResponse {

    private Errors errors;

    private String message;

    public ErrorResponse(Errors errors) {
        this.errors = errors;
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse of(Errors errors) {
        return new ErrorResponse(errors);
    }

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }

}
