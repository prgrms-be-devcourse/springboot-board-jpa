package com.kdt.jpa.web.exception;

class ValidationError {
	private String field;
	private String code;
	private Object rejectedValue;
	private String message;

	public ValidationError(String field, String code, Object rejectedValue, String message) {
		this.field = field;
		this.code = code;
		this.rejectedValue = rejectedValue;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getCode() {
		return code;
	}

	public Object getRejectedValue() {
		return rejectedValue;
	}

	public String getMessage() {
		return message;
	}
}
