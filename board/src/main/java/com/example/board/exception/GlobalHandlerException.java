package com.example.board.exception;

import com.example.board.dto.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResult<String> noSuchElement() {
        return ApiResult.failOf("존재하지 않는 정보입니다.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiResult<String> handlerArgumentException() {
        return ApiResult.failOf("값을 잘못 입력하셨습니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiResult<String> emptyTitleException(MethodArgumentNotValidException e) {
        return ApiResult.failOf(e.getMessage());
    }
}