package org.programmers.kdtboard.controller.response;

import org.springframework.http.HttpStatus;

public enum ErrorCodeMessage {
	POST_ID_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 POST ID 입니다."),
	USER_ID_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 USER ID 입니다."),
	INVALID_REQUEST_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력입니다.");

	private final HttpStatus statusCode;
	private final String message;

	ErrorCodeMessage(HttpStatus statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}
}
