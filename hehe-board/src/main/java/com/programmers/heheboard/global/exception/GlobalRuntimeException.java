package com.programmers.heheboard.global.exception;

import com.programmers.heheboard.global.codes.ErrorCode;

import lombok.Getter;

@Getter
public class GlobalRuntimeException extends RuntimeException {

	private final ErrorCode errorCode;

	public GlobalRuntimeException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
