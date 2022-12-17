package com.programmers.jpaboard.common.exception;

import javax.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

	public UserNotFoundException() {
		super("사용자를 조회할 수 없습니다.");
	}
}
