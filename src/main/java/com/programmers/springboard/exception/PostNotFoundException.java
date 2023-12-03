package com.programmers.springboard.exception;

public class PostNotFoundException extends CustomException {
	public PostNotFoundException() {
		super("400/00012", "post not found");
	}
}
