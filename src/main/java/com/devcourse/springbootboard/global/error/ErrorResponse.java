package com.devcourse.springbootboard.global.error;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import com.devcourse.springbootboard.global.error.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
	private final String status;
	private final String message;
	private final String errorClassName;
	private final List<FieldInvalidation> invalidFields;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private final LocalDateTime serverDateTime;

	@Builder
	public ErrorResponse(
		String status,
		String message,
		String errorClassName,
		List<FieldError> fieldErrors
	) {
		this.status = status;
		this.message = message;
		this.errorClassName = errorClassName;
		this.invalidFields = fieldErrors.stream()
			.map(fieldError -> FieldInvalidation
				.builder()
				.invalidField(fieldError.getField())
				.rejectedValue(fieldError.getRejectedValue())
				.errorMessage(fieldError.getDefaultMessage())
				.build()
			)
			.collect(Collectors.toList());
		this.serverDateTime = LocalDateTime.now();
	}

	public static ResponseEntity<ErrorResponse> toResponseEntity(
		ErrorCode errorCode,
		String errorClassName,
		List<FieldError> fieldErrors
	) {
		return ResponseEntity
			.badRequest()
			.body(
				ErrorResponse.builder()
					.status(errorCode.getHttpStatus().name())
					.message(errorCode.getMessage())
					.errorClassName(errorClassName)
					.fieldErrors(fieldErrors)
					.build()
			);
	}
}
