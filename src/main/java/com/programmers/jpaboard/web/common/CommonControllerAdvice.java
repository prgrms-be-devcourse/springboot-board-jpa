package com.programmers.jpaboard.web.common;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public ExceptionResult illegalExceptionHandler(IllegalArgumentException e) {
		return new ExceptionResult(e.getMessage(), LocalDateTime.now());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	public ExceptionResult exceptionHandler(Exception e) {
		return new ExceptionResult(e.getMessage(), LocalDateTime.now());
	}
}
