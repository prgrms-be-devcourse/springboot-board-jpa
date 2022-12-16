package com.programmers.jpaboard.common.exception;

public class PostNotFoundException extends RuntimeException {

	public PostNotFoundException() {
		super("게시글을 조회할 수 없습니다.");
	}
}
