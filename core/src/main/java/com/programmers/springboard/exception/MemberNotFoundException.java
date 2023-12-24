package com.programmers.springboard.exception;

public class MemberNotFoundException extends CustomException {
	public MemberNotFoundException() {
		super("400/00011", "member not found");
	}
}
