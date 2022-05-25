package com.prgrms.boardjpa.post;

public class NotExistException extends RuntimeException {
	public NotExistException() {
		super("존재하지 않습니다");
	}
}
