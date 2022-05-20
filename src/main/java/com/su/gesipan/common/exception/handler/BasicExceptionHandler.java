package com.su.gesipan.common.exception.handler;

import com.su.gesipan.common.exception.ErrorCode;
import com.su.gesipan.post.PostNotFoundException;
import com.su.gesipan.user.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.su.gesipan.common.exception.ErrorCode.ENTITY_NOT_FOUND;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class BasicExceptionHandler {

    // 지원하지 않은 HTTP method 호출 할 경우
    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ErrorCode handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("BasicExceptionHandler - HttpRequestMethodNotSupportedException", e);
        return ErrorCode.METHOD_NOT_ALLOWED;
    }

    // 최종 잡히지 않은 오류 핸들링
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ErrorCode handleException(Exception e) {
        log.error("BasicExceptionHandler - EntityNotFoundException", e);
        return ErrorCode.INTERNAL_SERVER_ERROR;
    }
}
