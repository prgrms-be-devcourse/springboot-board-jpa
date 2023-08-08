package com.programmers.heheboard.global.response;

public final class ErrorResponse {
	private final int httpStatusCode;

	public ErrorResponse(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public int httpStatusCode() {
		return httpStatusCode;
	}
}
