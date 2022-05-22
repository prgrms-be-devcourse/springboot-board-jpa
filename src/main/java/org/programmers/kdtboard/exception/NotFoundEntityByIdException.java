package org.programmers.kdtboard.exception;

import org.programmers.kdtboard.controller.response.ErrorCode;

public class NotFoundEntityByIdException extends RuntimeException {

	private final ErrorCode errorCodeMessage;

	public NotFoundEntityByIdException(String message, ErrorCode errorCodeMessage) {
		super(message);
		this.errorCodeMessage = errorCodeMessage;
	}

	public ErrorCode getErrorCodeMessage() {
		return errorCodeMessage;
	}
}
