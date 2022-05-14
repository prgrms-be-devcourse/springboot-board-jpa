package com.prgrms.jpaboard.global.exception;

import com.prgrms.jpaboard.global.common.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponseDto errorResponse = ErrorResponseDto.multipleError(ErrorCode.BAD_REQUEST, e.getBindingResult());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    // ModelAttribute
    @ExceptionHandler(org.springframework.validation.BindException.class)
    public ResponseEntity<ErrorResponseDto> handleBindException(BindException e) {
        final ErrorResponseDto errorResponse = ErrorResponseDto.multipleError(ErrorCode.BAD_REQUEST, e.getBindingResult());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
