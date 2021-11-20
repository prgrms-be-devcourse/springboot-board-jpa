package com.poogle.board.controller;

import com.poogle.board.error.NotFoundException;
import com.poogle.board.error.ServiceRuntimeException;
import com.poogle.board.error.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.poogle.board.controller.ApiResult.fail;

@Slf4j
@ControllerAdvice
public class GeneralExceptionHandler {

    private ResponseEntity<ApiResult<?>> newResponse(Throwable throwable, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(fail(throwable, status), headers, status);
    }

    @ExceptionHandler({
            IllegalStateException.class, IllegalArgumentException.class
    })
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        log.debug("[*] Bad request exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowedException(Exception e) {
        return newResponse(e, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity<?> handleServiceRuntimeException(ServiceRuntimeException e) {
        if (e instanceof NotFoundException) {
            return newResponse(e, HttpStatus.NOT_FOUND);
        } else if (e instanceof UnauthorizedException) {
            return newResponse(e, HttpStatus.UNAUTHORIZED);
        } else {
            log.warn("[*] Unexpected service exception occurred: {}", e.getMessage(), e);
            return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler({
            Exception.class, RuntimeException.class
    })
    public ResponseEntity<?> handleException(Exception e) {
        log.error("[*] Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
