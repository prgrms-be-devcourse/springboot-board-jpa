package com.example.boardjpa.exception;

import com.example.boardjpa.exception.custom.FieldBlankException;
import com.example.boardjpa.exception.custom.RecordNotFoundException;
import com.example.boardjpa.exception.custom.ValueOutOfRangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return handleException(ex.getMessage(), ErrorCode.API_NOT_FOUND);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException ex) {
        return handleException(ex.getMessage(), ex.getErrorCode());
    }

    @ExceptionHandler(FieldBlankException.class)
    public ResponseEntity<ErrorResponse> handleFieldBlankException(FieldBlankException ex) {
        return handleException(ex.getMessage(), ex.getErrorCode());
    }

    @ExceptionHandler(ValueOutOfRangeException.class)
    public ResponseEntity<ErrorResponse> handleValueOutOfRangeException(ValueOutOfRangeException ex) {
        return handleException(ex.getMessage(), ex.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUncaughtException(Exception ex) {
        return handleException(ex.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> handleException(String msg, ErrorCode errorCode) {
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        log.warn("Exception : {} {}", errorResponse.getMessage(), msg);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
