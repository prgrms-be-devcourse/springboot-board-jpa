package com.programmers.springbootboard.exception;

public class ErrorResponseDto {
    private int status;
    private String message;

    public ErrorResponseDto(ErrorMessage errorMessage) {
        this.status = errorMessage.status().value();
        this.message = errorMessage.name();
    }

    public static ErrorResponseDto of(ErrorMessage errorMessage) {
        return new ErrorResponseDto(errorMessage);
    }
}
