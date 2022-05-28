package com.prgrms.jpaboard.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prgrms.jpaboard.global.error.ErrorCode;
import com.prgrms.jpaboard.global.error.exception.WrongFieldException;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {
    private int status;
    private String message;
    private FieldError error;
    private List errors;

    public ErrorResponseDto(int status, String message, FieldError error, List errors) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.errors = errors;
    }

    public static ErrorResponseDto singleError(ErrorCode errorCode) {
        return new ErrorResponseDto(errorCode.getStatus(), errorCode.getMessage(), null, null);
    }

    public static ErrorResponseDto singleError(ErrorCode errorCode, WrongFieldException wrongFieldException) {
        FieldError fieldError = new FieldError(wrongFieldException.getFieldName(), wrongFieldException.getValue(), wrongFieldException.getReason());

        return new ErrorResponseDto(errorCode.getStatus(), errorCode.getMessage(), fieldError, null);
    }

    public static ErrorResponseDto singleError(ErrorCode errorCode, String errorMessage) {
        return new ErrorResponseDto(errorCode.getStatus(), errorMessage, null, null);
    }

    public static ErrorResponseDto multipleError(ErrorCode errorCode, BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors()
                .stream().map(fieldError -> new FieldError(
                        String.format("%s.%s", fieldError.getObjectName(), fieldError.getField()),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        return new ErrorResponseDto(errorCode.getStatus(), errorCode.getMessage(), null, errors);
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
