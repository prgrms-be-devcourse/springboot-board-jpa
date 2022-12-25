package com.prgrms.board.global;

import com.prgrms.board.dto.response.ApiBindErrorResponse;
import com.prgrms.board.dto.response.ApiErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiControllerAdvice {
    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiErrorResponse illegalArgumentExceptionHandler(IllegalArgumentException e) {
        String message = getMessage(e.getMessage());

        log.warn("IllegalArgumentException 발생-> {}", message);

        return ApiErrorResponse.fail(message, 400);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RuntimeException.class)
    public ApiErrorResponse runTimeExceptionHandler(RuntimeException e) {
        String message = getMessage(e.getMessage());

        log.warn("RuntimeException 발생-> {}", message);

        return ApiErrorResponse.fail(message, 404);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiErrorResponse internalServerErrorHandler(Exception e) {
        String message = getMessage(e.getMessage());

        log.warn("Exception 발생-> {}", message);

        return ApiErrorResponse.fail(message, 500);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiBindErrorResponse bindExceptionHandler(BindException e) {
        FieldError fieldError = e.getFieldError();

        String message = getMessage(Objects.requireNonNull(fieldError).getDefaultMessage());

        log.warn("BindException 발생-> {}", message);

        return ApiBindErrorResponse.fail(
                fieldError.getObjectName(),
                fieldError.getField(),
                message,
                400);
    }

    private String getMessage(String errorMessage) {
        return messageSource.getMessage(errorMessage, null, Locale.KOREA);
    }

}
