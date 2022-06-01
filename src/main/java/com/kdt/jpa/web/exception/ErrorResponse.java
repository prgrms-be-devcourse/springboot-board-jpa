package com.kdt.jpa.web.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorResponse<T> {
	private String errorCode;
	private T body;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime dateTime;

	public String getErrorCode() {
		return errorCode;
	}

	public T getBody() {
		return body;
	}

	public ErrorResponse(String errorCode, T body) {
		this.errorCode = errorCode;
		this.body = body;
		dateTime = LocalDateTime.now();
	}

	public static <T> ErrorResponse<T> fail(String errorCode, T errData) {
		return new ErrorResponse<>(errorCode, errData);
	}
}
