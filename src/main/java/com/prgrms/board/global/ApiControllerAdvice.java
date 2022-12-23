package com.prgrms.board.global;

import com.prgrms.board.dto.response.ApiBindErrorResponse;
import com.prgrms.board.dto.response.ApiErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiControllerAdvice {
    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiErrorResponse illegalArgumentExceptionHandler(IllegalArgumentException e) {
        String message = messageSource.getMessage(e.getMessage(), null, Locale.KOREA);
        log.warn("IllegalArgumentException 발생-> {}", message);
        return ApiErrorResponse.fail(message, 400);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RuntimeException.class)
    public ApiErrorResponse runTimeExceptionHandler(RuntimeException e) {
        String message = messageSource.getMessage(e.getMessage(), null, Locale.KOREA);
        log.warn("RuntimeException 발생-> {}", message);
        return ApiErrorResponse.fail(message, 404);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiErrorResponse internalServerErrorHandler(Exception e) {
        String message = messageSource.getMessage(e.getMessage(), null, Locale.KOREA);
        log.warn("Exception 발생-> {}", message);
        return ApiErrorResponse.fail(message, 500);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiBindErrorResponse bindExceptionHandler(BindException e) {
        log.warn("BindException 발생-> {}", e.getFieldError().getDefaultMessage());

        return ApiBindErrorResponse.fail(
                e.getFieldError().getObjectName(),
                e.getFieldError().getField(),
                e.getFieldError().getDefaultMessage(),
                400);
    }
}
