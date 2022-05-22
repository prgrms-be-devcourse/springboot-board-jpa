package org.programmers.kdtboard.controller.response;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
	POST_ID_NOT_FOUND(HttpStatus.BAD_REQUEST),
	USER_ID_NOT_FOUND(HttpStatus.BAD_REQUEST),
	INVALID_REQUEST_VALUE(HttpStatus.BAD_REQUEST);

	private final HttpStatus statusCode;

	ErrorCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

}
