package com.example.board.exception;

import com.example.board.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleRuntimeException(Exception e) {
        log.error(e.getMessage(), e);
        CustomError customError = CustomError.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(customError.getCode(), customError.getMessage());
        return ResponseEntity.internalServerError().body(ApiResponse.fail(errorResponse));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleCustomException(CustomException e) {
        CustomError customError = e.getCustomError();
        ErrorResponse errorResponse = new ErrorResponse(customError.getCode(), customError.getMessage());
        return ResponseEntity.status(customError.getStatus()).body(ApiResponse.fail(errorResponse));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        //TODO: 리스트로 에러 메세지 전달
        ErrorResponse errorResponse = new ErrorResponse("INVALID_INPUT_VALUE", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.status(status).body(ApiResponse.fail(errorResponse));
    }
}
