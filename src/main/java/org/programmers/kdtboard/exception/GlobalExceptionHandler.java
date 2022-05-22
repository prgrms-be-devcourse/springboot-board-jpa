package org.programmers.kdtboard.exception;

import org.programmers.kdtboard.controller.response.ErrorCode;
import org.programmers.kdtboard.controller.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundEntityByIdException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundEntityByIdException(final NotFoundEntityByIdException e) {
		log.warn("handleNotFoundEntityByIdException : {}", e.getMessage(), e);

		return ResponseEntity.status(e.getErrorCodeMessage().getStatusCode())
			.body(new ErrorResponse(e.getErrorCodeMessage(), e.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
		final MethodArgumentNotValidException e) {
		var bindingResult = e.getBindingResult();
		var stringBuilder = new StringBuilder();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			stringBuilder.append("에러 필드 -> ")
				.append(fieldError.getField())
				.append(", 에러 메세지 -> ")
				.append(fieldError.getDefaultMessage())
				.append(", 입력된 값 -> ")
				.append(fieldError.getRejectedValue());
		}
		log.warn("handleMethodArgumentNotValidException -> {}", stringBuilder, e);

		return ResponseEntity.status(ErrorCode.INVALID_REQUEST_VALUE.getStatusCode())
			.body(new ErrorResponse(ErrorCode.INVALID_REQUEST_VALUE, stringBuilder.toString()));
	}
}
