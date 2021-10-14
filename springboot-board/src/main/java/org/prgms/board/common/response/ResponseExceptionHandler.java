package org.prgms.board.common.response;

import org.prgms.board.common.exception.NotFoundException;
import org.prgms.board.common.exception.NotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
        return ErrorResponse.toResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({NotMatchException.class})
    public ResponseEntity<ErrorResponse> handleNotMatch(NotMatchException ex) {
        return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST,
            ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
