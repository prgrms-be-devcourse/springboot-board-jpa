package com.programmers.springboard.post.exception;

import com.programmers.springboard.global.common.CustomException;

public class PostNotFoundException extends CustomException {
	public PostNotFoundException() {
		super("ERR400002", "Post not found");
	}
}
