package com.programmers.springboard.controller;

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

import com.programmers.springboard.exception.CustomException;
import com.programmers.springboard.exception.ErrorResponse;
import com.programmers.springboard.exception.MemberNotFoundException;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.");

		List<FieldError> fieldErrors = ex.getFieldErrors();
		fieldErrors.stream().forEach(error -> {
			errorResponse.addValidation(error.getField(), error.getDefaultMessage());
		});

		return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getCode(), e.getMessage());
		return new ResponseEntity(errorResponse, HttpStatus.valueOf(e.getCode()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception e){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 장애가 발생했습니다.");

		return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
