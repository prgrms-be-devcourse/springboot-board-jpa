package com.programmers.springboard.member.exception;

import com.programmers.springboard.global.common.CustomException;

public class DuplicateIdException extends CustomException {
	public DuplicateIdException() {
		super("ERR400004", "login ID already exists");
	}
}
