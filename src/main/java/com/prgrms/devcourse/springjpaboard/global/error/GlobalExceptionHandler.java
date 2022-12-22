package com.prgrms.devcourse.springjpaboard.global.error;

import static org.springframework.http.HttpStatus.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> EntityNotFoundException(
		com.prgrms.devcourse.springjpaboard.global.error.EntityNotFoundException e) {

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(e.getMessage())
			.build();

		return ResponseEntity.status(NOT_FOUND).body(errorResponse);

	}
}
