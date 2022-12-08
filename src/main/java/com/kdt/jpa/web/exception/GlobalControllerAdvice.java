package com.kdt.jpa.web.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kdt.jpa.exception.ErrorCode;
import com.kdt.jpa.exception.ServiceException;

@RestControllerAdvice
public class GlobalControllerAdvice {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ErrorResponse<String> serviceExceptionHandle(ServiceException exception) {
		this.log.debug(exception.getMessage(), exception);

		return ErrorResponse.fail(exception.getErrorCode().getCode(), exception.getErrorCode().getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ErrorResponse<List<ValidationError>> methodArgumentNotValidExceptionHandle(
		MethodArgumentNotValidException exception) {
		log.debug(exception.getMessage(), exception);

		List<ValidationError> errors = exception.getFieldErrors()
			.stream()
			.map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getCode(),
				fieldError.getRejectedValue(), fieldError.getDefaultMessage()))
			.collect(Collectors.toList());

		return ErrorResponse.fail(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getCode(), errors);
	}

	@ExceptionHandler({TransactionSystemException.class, ConstraintViolationException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ErrorResponse<List<ValidationError>> constraintViolationExceptionHandle(
		ConstraintViolationException exception) {
		log.info(exception.getMessage(), exception);

		List<ValidationError> collect = exception.getConstraintViolations()
			.stream()
			.map(constraintViolation -> new ValidationError(
				constraintViolation.getPropertyPath().toString(),
				constraintViolation.getConstraintDescriptor()
					.getAnnotation()
					.annotationType()
					.getSimpleName(),
				constraintViolation.getInvalidValue(),
				constraintViolation.getMessage()))
			.collect(Collectors.toList());

		return ErrorResponse.fail(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getCode(), collect);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private ErrorResponse<String> unexpectedExceptionHandle(Exception exception) {
		this.log.error(exception.getMessage(), exception);

		return ErrorResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
			ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
	}
}
