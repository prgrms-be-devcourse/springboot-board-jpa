package com.su.gesipan.common.exception.handler;

import com.su.gesipan.common.exception.ErrorCode;
import com.su.gesipan.post.PostNotFoundException;
import com.su.gesipan.user.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
public class NotFoundHandler {

    // Post 를 찾지 못했을 경우
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    protected ErrorCode handledPostNotFoundException(PostNotFoundException e) {
        log.error("BasicExceptionHandler - PostNotFoundException", e);
        return ErrorCode.ENTITY_NOT_FOUND;
    }
    // User 를 찾지 못했을 경우
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    protected ErrorCode handledUserNotFoundException(UserNotFoundException e) {
        log.error("BasicExceptionHandler - UserNotFoundException", e);
        return ErrorCode.ENTITY_NOT_FOUND;
    }
}
