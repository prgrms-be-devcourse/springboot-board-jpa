package com.programmers.jpaboard.exception;

import com.programmers.jpaboard.dto.ApiResponse;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiResponse<Map<String, Object>> handleBusinessException(BusinessException e) {
        String errorMessage = e.getMessage();
        Map<String, Object> rejectedValuesMap = e.getRejectedValuesMap();

        log.error("사용자 입력 예외 발생 : {} | 원인 : {}", errorMessage, rejectedValuesMap, e);

        return ApiResponse.fail(errorMessage, rejectedValuesMap);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiResponse<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("허용되지 않은 HTTP Method 요청 발생 : {}", e.getMessage(), e);

        return ApiResponse.fail("잘못된 요청입니다.");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus
    protected ApiResponse<Void> handleException(Exception e) {
        log.error("알 수 없는 에러 발생 : {}", e.getMessage(), e);

        return ApiResponse.fail("알 수 없는 에러가 발생했습니다. 이용에 불편을 드려 죄송합니다.");
    }
}
