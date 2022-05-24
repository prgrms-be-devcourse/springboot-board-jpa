package com.prgrms.boardjpa.commons.exception;

public class CreationFailException extends RuntimeException {
	public CreationFailException() {
		super("생성에 실패하였습니다");
	}
}
