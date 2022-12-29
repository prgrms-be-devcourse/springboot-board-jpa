package com.spring.board.springboard.user;

import com.spring.board.springboard.domain.ErrorResponse;
import com.spring.board.springboard.user.exception.AuthenticateException;
import com.spring.board.springboard.user.exception.NoMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(NoMemberException.class)
    public ResponseEntity<ErrorResponse> handleNoMemberException(NoMemberException noMemberException){
        ErrorResponse errorResponse = new ErrorResponse(noMemberException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(AuthenticateException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticateException(AuthenticateException authenticateException){
        ErrorResponse errorResponse = new ErrorResponse(authenticateException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
