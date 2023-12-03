package com.programmers.springboard.member.exception;

import com.programmers.springboard.global.common.CustomException;

public class LoginFailException extends CustomException {
	public LoginFailException() {
		super("ERR400003", "Login failed");
	}
}
