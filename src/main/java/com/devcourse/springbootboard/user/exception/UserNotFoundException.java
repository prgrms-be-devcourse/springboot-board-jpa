package com.devcourse.springbootboard.user.exception;

import com.devcourse.springbootboard.global.error.exception.ErrorCode;

public class UserNotFoundException extends RuntimeException {
	private final ErrorCode errorCode;

	public UserNotFoundException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
