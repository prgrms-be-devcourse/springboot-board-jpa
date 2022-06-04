package org.programmers.kdtboard.controller.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private final LocalDateTime timestamp = LocalDateTime.now();
	private final int status;
	private final String error;
	private final String code;
	private final String message;
	private final List<BindErrorField> errorFields;

	public ErrorResponse(ErrorCode errorCodeMessage, String message) {
		this.status = errorCodeMessage.getStatusCode().value();
		this.error = errorCodeMessage.getStatusCode().name();
		this.code = errorCodeMessage.name();
		this.message = message;
		this.errorFields = new ArrayList<>();
	}

	public ErrorResponse(ErrorCode errorCodeMessage, String message, List<BindErrorField> errorFields) {
		this.status = errorCodeMessage.getStatusCode().value();
		this.error = errorCodeMessage.getStatusCode().name();
		this.code = errorCodeMessage.name();
		this.message = message;
		this.errorFields = errorFields;
	}

	public record BindErrorField(String field, String value, String message) {
	}

	public static List<BindErrorField> bindErrorFields(BindingResult bindingResult) {
		return bindingResult.getFieldErrors().stream()
			.map(error -> new BindErrorField(error.getField(),
				Objects.requireNonNull(error.getRejectedValue()).toString(),
				error.getDefaultMessage()))
			.toList();
	}
}
