package com.programmers.heheboard.global.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.programmers.heheboard.global.codes.ErrorCode;
import com.programmers.heheboard.global.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(GlobalRuntimeException.class)
	public ErrorResponse handleRuntimeException(GlobalRuntimeException ex) {
		ErrorCode errorCode = ex.getErrorCode();
		log.error("code: {} \n stack trace: {}", errorCode, ex);
		return new ErrorResponse(errorCode.getHttpStatusCode());
	}
}
