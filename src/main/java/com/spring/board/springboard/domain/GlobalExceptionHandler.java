package com.spring.board.springboard.domain;

import com.spring.board.springboard.post.exception.NoAuthorizedException;
import com.spring.board.springboard.post.exception.NoPostException;
import com.spring.board.springboard.user.exception.AuthenticateException;
import com.spring.board.springboard.user.exception.NoMemberException;
import com.spring.board.springboard.user.exception.SessionCreateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorResponse>> handleDtoValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<ValidationErrorResponse> responses = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationErrorResponse(
                                fieldError.getField(),
                                fieldError.getDefaultMessage()
                        )
                ).toList();

        return ResponseEntity.badRequest()
                .body(responses);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleEntityValidationException(IllegalArgumentException illegalArgumentException) {
        ErrorResponse errorResponse = new ErrorResponse(illegalArgumentException.getMessage());

        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(NoPostException.class)
    public ResponseEntity<ErrorResponse> handleNoPostException(NoPostException noPostException) {
        ErrorResponse errorResponse = new ErrorResponse(noPostException.getMessage());

        return ResponseEntity.status(
                        HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(NoMemberException.class)
    public ResponseEntity<ErrorResponse> handleNoMemberException(NoMemberException noMemberException) {
        ErrorResponse errorResponse = new ErrorResponse(noMemberException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(AuthenticateException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticateException(AuthenticateException authenticateException) {
        ErrorResponse errorResponse = new ErrorResponse(authenticateException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    @ExceptionHandler(SessionCreateException.class)
    public ResponseEntity<ErrorResponse> handleSessionCreateException(SessionCreateException sessionCreateException) {
        ErrorResponse errorResponse = new ErrorResponse(sessionCreateException.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler(NoAuthorizedException.class)
    public ResponseEntity<ErrorResponse> handleNoAuthorizedException(NoAuthorizedException noAuthorizedException) {
        ErrorResponse errorResponse = new ErrorResponse(noAuthorizedException.getMessage());

        return ResponseEntity.status(
                        HttpStatus.FORBIDDEN)
                .body(errorResponse);
    }
}
