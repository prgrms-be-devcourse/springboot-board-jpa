package com.devcourse.springbootboard.global.error;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FieldInvalidation {
	private final String invalidField;
	private final Object rejectedValue;
	private final String errorMessage;

	@Builder
	public FieldInvalidation(
		String invalidField,
		Object rejectedValue,
		String errorMessage
	) {
		this.invalidField = invalidField;
		this.rejectedValue = rejectedValue;
		this.errorMessage = errorMessage;
	}
}
