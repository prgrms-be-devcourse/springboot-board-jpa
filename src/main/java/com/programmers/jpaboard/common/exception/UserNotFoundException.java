package com.programmers.jpaboard.common.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super("사용자를 조회할 수 없습니다.");
	}
}
