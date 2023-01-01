package com.programmers.jpaboard.common.exception;

import javax.persistence.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {

	public PostNotFoundException() {
		super("게시글을 조회할 수 없습니다.");
	}
}
