package com.example.boardbackend.common.error;

import com.example.boardbackend.common.error.exception.IllegalArgException;
import com.example.boardbackend.common.error.exception.NotFoundException;
import com.example.boardbackend.common.error.response.NotValidResponse;
import com.example.boardbackend.common.error.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ErrorHandler {

    // Bean Validation에서 터지는 에러
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public NotValidResponse notValid(MethodArgumentNotValidException e){
        // 오류난 입력값이 여러개여도 하나씩만 보여준다.
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        return NotValidResponse.builder()
                .field(fieldError.getField())               // 오류난 필드명
                .value(fieldError.getRejectedValue())       // 거절된 입력된 값
                .message(fieldError.getDefaultMessage())    // 내가 설정한 메시지
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(Exception e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)   // 404
                .body(
                        ErrorResponse.builder()
                                .message(e.getLocalizedMessage())               // 오류가 터진 곳에서 재정의한 메시지를 가져옴
                                .exceptionType(e.getClass().getSimpleName())    // Exception 타입을 반환 (Exception 종류가 많아지면 유용)
                                .build()
                );
    }

    @ExceptionHandler(IllegalArgException.class)
    public ResponseEntity<ErrorResponse> illegalArg(Exception e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)   // 400
                .body(
                        ErrorResponse.builder()
                                .message(e.getLocalizedMessage())
                                .exceptionType(e.getClass().getSimpleName())
                                .build()
                );
    }


}
