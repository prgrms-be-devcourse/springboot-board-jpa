package com.programmers.springboard.exception;

public class MemberNotFoundException extends CustomException {
	public MemberNotFoundException() {
		super("ERR400001", "Member not found");
	}
}
