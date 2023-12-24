package com.programmers.springboard.exception;

public class GroupNotFoundException extends CustomException {
	public GroupNotFoundException() {
		super("400/00013", "group not found");
	}
}
