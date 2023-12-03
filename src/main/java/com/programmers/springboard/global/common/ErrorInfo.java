package com.programmers.springboard.global.common;

public record ErrorInfo(String errorMessage) {
	public static ErrorInfo of(String errorMessage) {
		return new ErrorInfo(errorMessage);
	}
}
