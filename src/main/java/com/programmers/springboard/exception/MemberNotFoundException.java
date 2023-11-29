package com.programmers.springboard.exception;

public class MemberNotFoundException extends CustomException {
	public MemberNotFoundException() {
		super(4001, "member not found");
	}
}
