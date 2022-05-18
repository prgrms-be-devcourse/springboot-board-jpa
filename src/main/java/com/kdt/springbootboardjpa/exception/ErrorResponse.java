package com.kdt.springbootboardjpa.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public class ErrorResponse {

    private final HttpStatus code;
    private final LocalDateTime timestamp;
    private final String message;
    private final int status;
}
