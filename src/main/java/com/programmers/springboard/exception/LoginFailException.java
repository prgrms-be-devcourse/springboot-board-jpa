package com.programmers.springboard.exception;

public class LoginFailException extends CustomException {
	public LoginFailException() {
		super("ERR400003", "Login failed");
	}
}
