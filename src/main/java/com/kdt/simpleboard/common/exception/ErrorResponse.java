package com.kdt.simpleboard.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class ErrorResponse {
    private final int status;
    private final String error;
    private final String message;


    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.error = errorCode.getStatus().name();
        this.message = errorCode.getMessage();
    }

    public ErrorResponse(String message) {
        this.status = HttpStatus.BAD_REQUEST.value();
        this.error = "REQUEST_FORMAT_ERROR";
        this.message = message;
    }
}

