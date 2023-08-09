package com.jpaboard.global.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class FieldErrorInfo {
    private final String field;
    private final String value;
    private final String reason;

    private FieldErrorInfo(String field, String value, String reason) {
        this.field = field;
        this.value = value;
        this.reason = reason;
    }

    public static List<FieldErrorInfo> create(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.stream()
                .map(error -> new FieldErrorInfo(
                        error.getField(),
                        error.getRejectedValue() == null ? null : error.getRejectedValue().toString(),
                        error.getDefaultMessage()))
                .toList();
    }
}
