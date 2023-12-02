package com.programmers.springboard.exception;

public record ErrorInfo(String errorMessage) {
	public static ErrorInfo of(String errorMessage) {
		return new ErrorInfo(errorMessage);
	}
}
