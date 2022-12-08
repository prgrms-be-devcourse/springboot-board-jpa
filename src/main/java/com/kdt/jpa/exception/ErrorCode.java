package com.kdt.jpa.exception;

public enum ErrorCode {
	INTERNAL_SERVER_ERROR("C0001", "Internal Server Error"),
	POST_NOT_FOUND("P0001", "Not found post"),
	MEMBER_NOT_FOUND("M0001", "Not found member"),
	METHOD_ARGUMENT_NOT_VALID("V0001", "Method argument not valid"),
	CONSTRAINT_VIOLATION("V0002", "Constraint violation Error");
	private final String code;
	private final String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}
}
