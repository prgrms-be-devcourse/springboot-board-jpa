package me.kimihiqq.springbootboardjpa.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.springbootboardjpa.global.exception.PostException;
import me.kimihiqq.springbootboardjpa.global.exception.UserException;
import me.kimihiqq.springbootboardjpa.global.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handlePostException(PostException e) {
        log.error("PostException Occurred: {}", e.getMessage(), e);

        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode().getMessage(), e.getDebugMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
        log.error("UserException Occurred: {}", e.getMessage(), e);

        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode().getMessage(), e.getDebugMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

