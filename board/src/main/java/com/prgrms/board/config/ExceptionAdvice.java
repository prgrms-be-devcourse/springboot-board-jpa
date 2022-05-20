package com.prgrms.board.config;

import com.prgrms.board.common.ApiResponse;
import com.prgrms.board.common.exception.MaxPostException;
import com.prgrms.board.common.exception.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.prgrms.board.common.ResponseMessage.MAX_POST_EXCEPTION;
import static com.prgrms.board.common.ResponseMessage.NOT_FOUND_EXCEPTION;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MaxPostException.class)
    public ApiResponse<String> maxPostException(MaxPostException e) {
        return ApiResponse.fail(MAX_POST_EXCEPTION.getCode(), MAX_POST_EXCEPTION.getMsg());
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundException(NotFoundException e) {
        return ApiResponse.fail(NOT_FOUND_EXCEPTION.getCode(), NOT_FOUND_EXCEPTION.getMsg());
    }
}
