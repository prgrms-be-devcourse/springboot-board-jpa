package kdt.prgms.springbootboard.global.error;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
public class ErrorResponse {

    private final String message;
    private final int status;
    private final List<CustomFieldError> errors;
    private final String code;


    private ErrorResponse(ErrorCode code, List<CustomFieldError> errors) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.errors = errors;
        this.code = code.getCode();
    }

    private ErrorResponse(ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = new ArrayList<>();
    }


    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, CustomFieldError.of(bindingResult));
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    @Getter
    public static class CustomFieldError {
        private final String field;
        private final String value;
        private final String reason;

        private CustomFieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        private static List<CustomFieldError> of(final BindingResult bindingResult) {
            final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                .map(error -> new CustomFieldError(
                    error.getField(),
                    error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                    error.getDefaultMessage()))
                .toList();
        }
    }
}
