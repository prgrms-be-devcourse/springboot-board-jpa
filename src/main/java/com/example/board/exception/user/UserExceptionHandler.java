package com.example.board.exception.user;

import com.example.board.controller.UserController;
import com.example.board.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice(basePackageClasses = UserController.class)
@Slf4j
public class UserExceptionHandler {

    private static final HttpStatus errorStatus = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ErrorMessage> notFoundUserException(
            NotFoundUserException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, errorStatus));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        String errorMessage = String.join(", ", errors);

        return ResponseEntity.badRequest()
                .body(new ErrorMessage(exception, errorMessage, errorStatus));
    }
}
