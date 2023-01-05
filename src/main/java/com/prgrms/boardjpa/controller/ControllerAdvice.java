package com.prgrms.boardjpa.controller;

import com.prgrms.boardjpa.exception.EntityNotFoundFromRepositoryException;
import com.prgrms.boardjpa.exception.ErrorMessage;
import com.prgrms.boardjpa.exception.UnexpectableErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(EntityNotFoundFromRepositoryException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundFromRepositoryException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleUnexpectableException(Exception e, HttpServletRequest request) {
        UnexpectableErrorMessage errorMessage = new UnexpectableErrorMessage(request, e);
        log.error(errorMessage.getMessage());
        ErrorMessage userErrorMessage = new ErrorMessage("예상하지 못한 에러입니다");
        return ResponseEntity.internalServerError().body(userErrorMessage);
    }
}
