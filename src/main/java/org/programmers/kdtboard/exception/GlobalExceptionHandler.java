package org.programmers.kdtboard.exception;

import org.programmers.kdtboard.controller.response.ErrorCodeMessage;
import org.programmers.kdtboard.controller.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(final EntityNotFoundException e) {
		log.error("handleEntityNotFoundException : {}", e.getMessage());

		return ResponseEntity.status(e.getErrorCodeMessage().getStatusCode())
			.body(new ErrorResponse(e.getErrorCodeMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleBindException(final BindException e) {
		var bindingResult = e.getBindingResult();
		var stringBuilder = new StringBuilder();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			stringBuilder.append("에러 필드 : ")
				.append(fieldError.getField())
				.append(" 에러 메세지 : ")
				.append(fieldError.getDefaultMessage())
				.append(" 입력된 값 : ")
				.append(fieldError.getRejectedValue());
		}
		log.error("handleBindException -> {} \n {}", stringBuilder, e.getMessage());

		return ResponseEntity.status(ErrorCodeMessage.INVALID_REQUEST_VALUE.getStatusCode())
			.body(new ErrorResponse(ErrorCodeMessage.INVALID_REQUEST_VALUE));
	}
}
