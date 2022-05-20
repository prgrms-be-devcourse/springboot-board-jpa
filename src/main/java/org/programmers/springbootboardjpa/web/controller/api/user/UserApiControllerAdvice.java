package org.programmers.springbootboardjpa.web.controller.api.user;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootboardjpa.domain.user.exception.IllegalBirthDateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = UserApiController.class)
public class UserApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ApiErrorData handleIllegalVoucherTypeException(IllegalBirthDateException e) {
        log.warn("API 오류 응답: 잘못된 생일 값", e);
        return new ApiErrorData("-103", "User에 대해 잘못된 생일 날짜 전달");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ApiErrorData handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("API 오류 응답: 잘못된 인자 전달", e);
        return new ApiErrorData("-102", "User API에 대하여 잘못된 요청 값 전달");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ApiErrorData handleOtherException(Exception e) {
        log.error("API 오류 응답: 식별되지 않은 오류: ", e);
        return new ApiErrorData("-101", "User API 관련하여 식별되지 않은 오류가 발생");
    }

    record ApiErrorData(String error_code, String error_description) {
    }
}