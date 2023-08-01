package com.programmers.springbootboardjpa.global.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private final HttpStatus status;
    private final Integer statusCode;
    private final String errorMessage;

    @Builder
    public ErrorResponse(HttpStatus status, Integer statusCode, String errorMessage) {
        this.status = status;
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

}
