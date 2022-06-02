package com.prgrms.boardjpa.core.commons.exception;

public class NotExistException extends RuntimeException {
	public NotExistException() {
		super("존재하지 않습니다");
	}
}
