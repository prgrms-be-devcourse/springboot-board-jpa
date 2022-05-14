package com.prgrms.jpaboard.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prgrms.jpaboard.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {
    private int status;
    private String message;
    private List errors;

    public ErrorResponseDto(int status, String message, List errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public static ErrorResponseDto singleError(ErrorCode errorCode) {
        return new ErrorResponseDto(errorCode.getStatus(), errorCode.getMessage(), null);
    }

    public static ErrorResponseDto multipleError(ErrorCode errorCode, BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors()
                .stream().map(fieldError -> new FieldError(
                        String.format("%s.%s", fieldError.getObjectName(), fieldError.getField()),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        return new ErrorResponseDto(errorCode.getStatus(), errorCode.getMessage(), errors);
    }

    @Getter
    public static class FieldError<T> {
        private String field;
        private T value;
        private String reason;

        public FieldError(String field, T value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }
}
