package com.prgrms.board.global.exception;

import com.prgrms.board.global.common.ErrorCode;

public class CustomException extends RuntimeException {

	private final ErrorCode error;

	public CustomException(ErrorCode error) {
		super(error.getMessage());
		this.error = error;
	}
}
