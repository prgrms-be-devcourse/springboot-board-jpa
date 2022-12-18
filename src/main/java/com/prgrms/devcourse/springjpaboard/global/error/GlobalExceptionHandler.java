package com.prgrms.devcourse.springjpaboard.global.error;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> EntityNotFoundException(EntityNotFoundException e) {

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(e.getMessage())
			.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

	}
}
