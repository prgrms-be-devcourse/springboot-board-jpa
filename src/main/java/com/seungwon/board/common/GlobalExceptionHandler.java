package com.seungwon.board.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.seungwon.board.common.exception.InvalidDataException;
import com.seungwon.board.common.exception.InvalidRequestException;
import com.seungwon.board.common.exception.NoSuchDataException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(NoSuchDataException.class)
	public ResponseEntity<Object> noSuchDataException(NoSuchDataException ex,
			WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.status(HttpStatus.NOT_FOUND)
				.message(ex.getMessage())
				.build();

		return handleExceptionInternal(ex, errorResponse, null, HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<Object> invalidRequestException(InvalidRequestException ex,
			WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.status(HttpStatus.BAD_REQUEST)
				.message(ex.getMessage())
				.build();

		return handleExceptionInternal(ex, errorResponse, null, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<Object> invalidDataException(InvalidDataException ex,
			WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.message(ex.getMessage())
				.build();

		return handleExceptionInternal(ex, errorResponse, null, HttpStatus.METHOD_NOT_ALLOWED, request);
	}

}
