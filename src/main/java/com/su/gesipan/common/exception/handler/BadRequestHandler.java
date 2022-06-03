package com.su.gesipan.common.exception.handler;

import com.su.gesipan.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.su.gesipan.common.exception.ErrorCode.INVALID_INPUT_VALUE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class BadRequestHandler {

    // @Valid 실패
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected BindingResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("BadRequestHandler - MethodArgumentNotValidException", e);
        return e.getBindingResult();
    }

    // Controller 함수 인자 Mismatch
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("BadRequestHandler - MethodArgumentTypeMismatchException", e);
        return e.getMessage();
    }

    // checkArgument 함수 실패시 발생
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    protected ErrorCode handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("BadRequestHandler - IllegalArgumentException", e);
        return INVALID_INPUT_VALUE;
    }
}
