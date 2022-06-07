package org.programmers.kdtboard.exception;

import org.programmers.kdtboard.controller.response.ErrorCode;

public class NotValidException extends IllegalArgumentException {
	private final ErrorCode errorCode;

	public NotValidException(ErrorCode errorCode,String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
