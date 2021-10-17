package com.devcourse.springbootboard.global.error;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.devcourse.springbootboard.global.error.exception.ErrorCode;
import com.devcourse.springbootboard.post.exception.PostNotFoundException;
import com.devcourse.springbootboard.user.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = {Exception.class})
	protected ResponseEntity<ErrorResponse> handleException(Exception e) {
		return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR, "", Collections.emptyList());
	}

	@ExceptionHandler(value = {PostNotFoundException.class})
	protected ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
		return ErrorResponse.toResponseEntity(e.getErrorCode(), "", Collections.emptyList());
	}

	@ExceptionHandler(value = {UserNotFoundException.class})
	protected ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
		return ErrorResponse.toResponseEntity(e.getErrorCode(), "", Collections.emptyList());
	}

	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
		List<FieldError> fieldErrors = e.getFieldErrors();
		String errorClassName = fieldErrors.isEmpty() ? "" : fieldErrors.get(0).getObjectName();

		return ErrorResponse.toResponseEntity(
			ErrorCode.INVALID_INPUT,
			errorClassName,
			fieldErrors
		);
	}
}
