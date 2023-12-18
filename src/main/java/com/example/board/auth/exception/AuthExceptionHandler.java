package com.example.board.auth.exception;


import com.example.board.dto.response.ApiResponse;
import com.example.board.dto.response.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice("com.example.board.auth")
public class AuthExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ApiResponse<Object>> handleTokenException(TokenException e) {
        ResponseStatus errorResponseStatus = e.getErrorResponseStatus();
        return ResponseEntity.status(errorResponseStatus.getHttpStatus()).body(ApiResponse.fail(errorResponseStatus));
    }
}
