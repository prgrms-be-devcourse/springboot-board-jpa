package com.assignment.bulletinboard.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final String message;

    private Errors errors;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    public ErrorResponse(String message) {
        this.message = message;
        this.serverDatetime = LocalDateTime.now();
    }

    public ErrorResponse(String message, Errors errors) {
        this.message = message;
        this.errors = errors;
        this.serverDatetime = LocalDateTime.now();
    }

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse of(String message, Errors errors) {
        return new ErrorResponse(message, errors);
    }
}
