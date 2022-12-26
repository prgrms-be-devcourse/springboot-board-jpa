package com.devcourse.springbootboardjpa.common.exception;

import com.devcourse.springbootboardjpa.common.dto.ErrorResponse;
import com.devcourse.springbootboardjpa.common.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDTO> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();

        return new ResponseEntity<>(
                new ErrorResponse(errorCode),
                HttpStatus.valueOf(errorCode.getStatus())
        );
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindingException(BindException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
