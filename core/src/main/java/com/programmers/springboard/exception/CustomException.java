package com.programmers.springboard.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
	private String code;
	private String message;

	@Override
	public String getMessage() {
		return message;
	}
}
