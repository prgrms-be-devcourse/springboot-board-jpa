package com.prgrms.board.domain.user.exception;

import com.prgrms.board.global.common.ErrorCode;
import com.prgrms.board.global.exception.CustomException;

public class UserNotFoundException extends CustomException {

	public UserNotFoundException(ErrorCode error) {
		super(error);
	}
}
