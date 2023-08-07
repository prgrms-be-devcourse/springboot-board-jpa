package com.programmers.heheboard.global.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.programmers.heheboard.global.codes.ErrorCode;
import com.programmers.heheboard.global.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ValidationExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleRuntimeException(MethodArgumentNotValidException ex) {
		ErrorCode errorCode = ErrorCode.VALIDATION_FAIL;
		log.info("code: {} \n", errorCode);
		return new ErrorResponse(errorCode.getHttpStatusCode());
	}
}
