package com.prgrms.boardjpa.commons.exception;

public class CreationFailException extends RuntimeException {
	public <T> CreationFailException(Class<T> clazz) {
		super(clazz.getSimpleName() + "생성에 실패하였습니다");
	}
}
