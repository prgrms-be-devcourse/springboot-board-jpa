package com.programmers.jpaboard.web.common;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ExceptionResult> noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
		ExceptionResult exceptionResult = new ExceptionResult(e.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(exceptionResult, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResult> validExceptionHandler(MethodArgumentNotValidException e) {
		ExceptionResult exceptionResult = new ExceptionResult(
			e.getBindingResult().getFieldError().getDefaultMessage(),
			LocalDateTime.now()
		);
		return new ResponseEntity<>(exceptionResult, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ExceptionResult> entityNotFoundExceptionHandler(EntityNotFoundException e) {
		ExceptionResult exceptionResult = new ExceptionResult(e.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(exceptionResult, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ExceptionResult> exceptionHandler(Exception e) {
		ExceptionResult exceptionResult = new ExceptionResult(e.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(exceptionResult, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
