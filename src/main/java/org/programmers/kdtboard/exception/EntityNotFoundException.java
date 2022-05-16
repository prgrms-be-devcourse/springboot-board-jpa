package org.programmers.kdtboard.exception;

import org.programmers.kdtboard.controller.response.ErrorCodeMessage;

public class EntityNotFoundException extends javax.persistence.EntityNotFoundException {

	private final ErrorCodeMessage errorCodeMessage;

	public EntityNotFoundException(String message, ErrorCodeMessage errorCodeMessage) {
		super(message);
		this.errorCodeMessage = errorCodeMessage;
	}

	public ErrorCodeMessage getErrorCodeMessage() {
		return errorCodeMessage;
	}
}
