package com.prgrms.devcourse.springjpaboard.global.error;

public class EntityNotFoundException extends RuntimeException {
	public EntityNotFoundException(String message) {
		super(message);
	}
}
