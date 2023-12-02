package com.programmers.springboard.exception;

public class PostNotFoundException extends CustomException {
	public PostNotFoundException() {
		super("ERR400002", "Post not found");
	}
}
