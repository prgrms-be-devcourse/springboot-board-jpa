package com.kdt.jpa.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kdt.jpa.exception.ErrorCode;
import com.kdt.jpa.exception.ServiceException;

@RestControllerAdvice
public class GlobalControllerAdvice {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ErrorResponse<String> serviceExceptionHandle(ServiceException exception) {
		this.log.debug(exception.getMessage(), exception);

		return ErrorResponse.fail(exception.getErrorCode().getCode(), exception.getErrorCode().getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private ErrorResponse<String> unexpectedExceptionHandle(Exception exception) {
		this.log.error(exception.getMessage(), exception);

		return ErrorResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
	}
}
