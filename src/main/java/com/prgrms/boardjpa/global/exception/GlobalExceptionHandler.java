package com.prgrms.boardjpa.global.exception;

import com.prgrms.boardjpa.global.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDomainConditionException.class)
    public ResponseEntity<ExceptionResponse> domainException(InvalidDomainConditionException e) {
        return ExceptionResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(BusinessServiceException.class)
    public ResponseEntity<ExceptionResponse> businessException(BusinessServiceException e) {
        return ExceptionResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> invalidMethodArgument(MethodArgumentNotValidException e) {
        ExceptionResponse<Object> exceptionResponse = ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            exceptionResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}