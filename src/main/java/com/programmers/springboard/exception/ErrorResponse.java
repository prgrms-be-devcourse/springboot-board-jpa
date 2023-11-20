package com.programmers.springboard.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
	private Integer code;
	private String message;
	private Map<String, String> result = new HashMap<>();

	public ErrorResponse(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public void addValidation(String field, String message) {
		result.put(field, message);
	}
}
