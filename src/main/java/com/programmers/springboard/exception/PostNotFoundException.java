package com.programmers.springboard.exception;

public class PostNotFoundException extends CustomException {
	public PostNotFoundException() {
		super(4002, "post not found");
	}
}
