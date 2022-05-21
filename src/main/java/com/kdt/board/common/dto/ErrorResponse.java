package com.kdt.board.common.dto;

import com.kdt.board.common.exception.ErrorCode;
import org.springframework.validation.BindingResult;

import java.util.List;

public class ErrorResponse {
    private String message;
    private int status;
    private List<FieldError> errors;

    private ErrorResponse(ErrorCode code, List<FieldError> errors) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.errors = errors;
    }

    private ErrorResponse(ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public static ErrorResponse of(ErrorCode code, BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public String getField() {
            return field;
        }

        public String getValue() {
            return value;
        }

        public String getReason() {
            return reason;
        }

        private static List<FieldError> of(BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .toList();
        }
    }
}
