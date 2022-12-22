package com.example.board.global.dto;

import com.example.board.util.MessageUtil;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public record BindingErrorResponse(String objectName,
                                   String field,
                                   String code,
                                   String message,
                                   LocalDateTime responsedAt) {

    public static BindingErrorResponse of(FieldError fieldError) {
        return new BindingErrorResponse(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getCode(),
                MessageUtil.getMessage(fieldError),
                now()
        );
    }
}
