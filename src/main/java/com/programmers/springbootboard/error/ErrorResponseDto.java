package com.programmers.springbootboard.error;

import lombok.NonNull;

import java.time.LocalDateTime;

public class ErrorResponseDto {
    @NonNull
    private int status;
    @NonNull
    private String message;
    @NonNull
    private LocalDateTime serverDateTime;

    public ErrorResponseDto(ErrorMessage errorMessage) {
        this.status = errorMessage.getStatus().value();
        this.message = errorMessage.name();
        this.serverDateTime = LocalDateTime.now();
    }

    public static ErrorResponseDto of(ErrorMessage errorMessage) {
        return new ErrorResponseDto(errorMessage);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getServerDateTime() {
        return serverDateTime;
    }
}
