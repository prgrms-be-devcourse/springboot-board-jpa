package com.prgrms.springboard.global.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prgrms.springboard.global.common.ApiResponse;
import com.prgrms.springboard.global.error.exception.InvalidValueException;
import com.prgrms.springboard.global.error.exception.NotFoundException;
import com.prgrms.springboard.post.exception.UserNotHavePermission;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleNotValidException(MethodArgumentNotValidException e) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), bindingResultMessage(e));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<String> handleNotFoundException(NotFoundException e) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(UserNotHavePermission.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<String> handleNotPermissionException(UserNotHavePermission e) {
        return ApiResponse.fail(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ExceptionHandler(InvalidValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleInvalidValueException(NotFoundException e) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> handleException(Exception e) {
        logger.error(e.getMessage());
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에서 에러가 발생했습니다.");
    }

    private String bindingResultMessage(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }
}
