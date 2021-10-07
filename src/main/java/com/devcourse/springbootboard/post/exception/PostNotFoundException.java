package com.devcourse.springbootboard.post.exception;

import com.devcourse.springbootboard.global.error.exception.ErrorCode;

import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException {
	private final ErrorCode errorCode;

	public PostNotFoundException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
