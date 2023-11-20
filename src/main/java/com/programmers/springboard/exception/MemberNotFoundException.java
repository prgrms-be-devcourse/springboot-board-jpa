package com.programmers.springboard.exception;

public class MemberNotFoundException extends CustomException {
	public MemberNotFoundException() {
		super(400, "member not found");
	}
}
