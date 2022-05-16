package org.prgrms.board.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {
    private final String message;
    private final int status;
    private final String code;
    private final List<FieldError> errors;

    private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = errors;
    }

    public ErrorResponse(ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = new ArrayList<>();
    }

    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(ErrorCode code, BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    @Getter
    public static class FieldError {
        private final String field;
        private final String value;
        private final String reason;

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        private static List<FieldError> of(BindingResult bindingResult) {
            return bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
