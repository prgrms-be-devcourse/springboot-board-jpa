package com.programmers.jpaboard.board.exhandler.advice;

import com.programmers.jpaboard.board.exception.BoardNotFoundException;
import com.programmers.jpaboard.board.exhandler.ErrorResult;
import com.programmers.jpaboard.board.exhandler.ErrorStatus;
import com.programmers.jpaboard.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestControllerAdvice(basePackages = "com.programmers.jpaboard.board")
public class BoardControllerAdvice {

    @ExceptionHandler
    public ApiResponse<ErrorResult> handleBoardNotFoundException(BoardNotFoundException exception) {
        ErrorResult errorResult = new ErrorResult(exception.getClass().getSimpleName(), exception.getMessage());
        return ApiResponse.fail(ErrorStatus.BOARD_NOT_FOUND.getCode(), ErrorStatus.BOARD_NOT_FOUND.getMessage(), errorResult);
    }

    @ExceptionHandler
    public ApiResponse<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new ConcurrentHashMap<>();
        exception.getBindingResult()
                .getAllErrors()
                .forEach(
                        error -> putError(error, errors)
                );

        return ApiResponse.fail(ErrorStatus.METHOD_ARGUMENT_NOT_VALID.getCode(), ErrorStatus.METHOD_ARGUMENT_NOT_VALID.getMessage(), errors);
    }

    private void putError(ObjectError error, Map<String, String> errors) {
        errors.put(((FieldError) error).getField(), error.getDefaultMessage());
    }

    @ExceptionHandler
    public ApiResponse<ErrorResult> handleException(Exception exception) {
        ErrorResult errorResult = new ErrorResult(exception.getClass().getSimpleName(), exception.getMessage());
        return ApiResponse.fail(ErrorStatus.INTERNAL_SERVER_ERROR.getCode(), ErrorStatus.INTERNAL_SERVER_ERROR.getMessage(), errorResult);
    }
}
