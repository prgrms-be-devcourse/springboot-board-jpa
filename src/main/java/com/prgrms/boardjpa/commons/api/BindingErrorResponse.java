package com.prgrms.boardjpa.commons.api;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.Getter;

@Getter
public class BindingErrorResponse {

	private final List<ErrorField> errors;

	private BindingErrorResponse(List<ErrorField> errors) {
		this.errors = errors;
	}

	public static BindingErrorResponse of(BindingResult bindingResult, MessageSource messageSource, Locale locale) {
		return new BindingErrorResponse(
			ErrorField.of(bindingResult, messageSource, locale));
	}

	@Getter
	private static class ErrorField {
		private final String field;
		private final String value;
		private final String message;

		public ErrorField(String field, String value, String message) {
			this.field = field;
			this.value = value;
			this.message = message;
		}

		public static List<ErrorField> of(BindingResult bindingResult, MessageSource messageSource, Locale locale) {
			return	bindingResult.getAllErrors().stream()
					.map(error ->
						new ErrorField(
							((FieldError)error).getField(),
							String.valueOf(((FieldError)error).getRejectedValue()),
							error.getDefaultMessage()))
					.collect(Collectors.toList());
		}
	}
}

