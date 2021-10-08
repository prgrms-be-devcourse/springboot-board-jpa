package com.devcourse.springbootboard.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.devcourse.springbootboard.post.exception.PostNotFoundException;
import com.devcourse.springbootboard.user.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(value = {PostNotFoundException.class})
	protected ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
		return ErrorResponse.toResponseEntity(e.getErrorCode());
	}

	@ExceptionHandler(value = {UserNotFoundException.class})
	protected ResponseEntity<ErrorResponse> handleUserNotFoundException(PostNotFoundException e) {
		return ErrorResponse.toResponseEntity(e.getErrorCode());
	}
}
