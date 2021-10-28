package com.programmers.springbootboard.error;

import lombok.NonNull;

import java.time.LocalDateTime;


public class ErrorResponseDto {
    @NonNull
    private final String message;
    @NonNull
    private final LocalDateTime serverDateTime;

    public ErrorResponseDto(ErrorMessage errorMessage) {
        this.message = errorMessage.name();
        this.serverDateTime = LocalDateTime.now();
    }

    public static ErrorResponseDto of(ErrorMessage errorMessage) {
        return new ErrorResponseDto(errorMessage);
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getServerDateTime() {
        return serverDateTime;
    }
}
