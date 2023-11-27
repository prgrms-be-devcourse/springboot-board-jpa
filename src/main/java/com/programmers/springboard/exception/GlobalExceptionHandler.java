package com.programmers.springboard.exception;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		logger.warn("Method argument not valid : ", ex);

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.");
		List<FieldError> fieldErrors = ex.getFieldErrors();
		fieldErrors.forEach(error -> errorResponse.addValidation(error.getField(), error.getDefaultMessage()));

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
		logger.warn("custom exception occured : ", ex);
		ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getCode()));
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
		HttpStatusCode statusCode, WebRequest request) {
		logger.error("exception occured : ", ex);
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 장애가 발생했습니다.");
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
