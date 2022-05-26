package com.prgrms.boardjpa.commons;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	private final MessageSource messageSource;

	public GlobalExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<BindingErrorResponse> handleBindingException(BindException bindException, Locale locale) {
		log.info("BindException {}", bindException.getMessage());

		return ResponseEntity.badRequest()
			.body(BindingErrorResponse.of(
				bindException.getBindingResult(), messageSource, locale));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
		log.warn("Unknown RuntimeException ", e);

		return ResponseEntity.badRequest()
			.body(new ErrorResponse(e));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		log.warn("Unknown Exception ", e);

		return ResponseEntity.badRequest()
			.body(new ErrorResponse(e));
	}
}