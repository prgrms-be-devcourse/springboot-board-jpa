package org.programmers.kdtboard.exception;

import org.programmers.kdtboard.controller.response.ErrorCode;
import org.programmers.kdtboard.controller.response.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ErrorResponse handleUnexpectedException(Exception e) {
		log.error("handleUnexpectedException : {}", e.getMessage(), e);

		return new ErrorResponse(ErrorCode.UNEXPECTED_ERROR, e.getMessage());
	}

	@ExceptionHandler(RuntimeException.class)
	public ErrorResponse handleUnexpectedRuntimeException(RuntimeException e) {
		log.error("handleUnexpectedRuntimeException : {}", e.getMessage(), e);

		return new ErrorResponse(ErrorCode.UNEXPECTED_ERROR, e.getMessage());
	}

	@ExceptionHandler(NotFoundEntityByIdException.class)
	public ErrorResponse handleNotFoundEntityByIdException(NotFoundEntityByIdException e) {
		log.warn("handleNotFoundEntityByIdException : {}", e.getMessage(), e);

		return new ErrorResponse(e.getErrorCodeMessage(), e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {
		var errorFields = ErrorResponse.bindErrorFields(e.getBindingResult());
		log.info("handleMethodArgumentNotValidException -> {}", errorFields, e);

		return new ErrorResponse(ErrorCode.INVALID_REQUEST_VALUE, "binding error", errorFields);
	}
}
