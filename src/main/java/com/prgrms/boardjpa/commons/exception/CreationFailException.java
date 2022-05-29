package com.prgrms.boardjpa.commons.exception;

public class CreationFailException extends RuntimeException {
	public CreationFailException(Class<?> clazz) {
		super(clazz.getSimpleName() + "생성에 실패하였습니다");
	}
}
