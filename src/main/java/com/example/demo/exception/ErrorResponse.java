package com.example.demo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private int status;
    private LocalDateTime occurredTime;

    public ErrorResponse(ErrorStatus errorCode) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
        this.occurredTime = LocalDateTime.now();
    }
}
