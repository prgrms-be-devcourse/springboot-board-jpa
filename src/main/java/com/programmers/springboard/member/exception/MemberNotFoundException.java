package com.programmers.springboard.member.exception;

import com.programmers.springboard.global.common.CustomException;

public class MemberNotFoundException extends CustomException {
	public MemberNotFoundException() {
		super("ERR400001", "Member not found");
	}
}
