package com.programmers.springboard.exception;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {
	private Integer code;
	private String message;
	private Map<String, String> validation = new HashMap<>();

	public ErrorResponse(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public void addValidation(String field, String message) {
		validation.put(field, message);
	}
}
