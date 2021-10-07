package com.devcourse.springbootboard.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.devcourse.springbootboard.post.exception.PostNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(value = {PostNotFoundException.class})
	protected ResponseEntity<ErrorResponse> handleDataException(PostNotFoundException e) {
		return ErrorResponse.toResponseEntity(e.getErrorCode());
	}

}
