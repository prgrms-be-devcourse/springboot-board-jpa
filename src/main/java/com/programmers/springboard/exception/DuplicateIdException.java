package com.programmers.springboard.exception;

public class DuplicateIdException extends CustomException {
	public DuplicateIdException() {
		super("ERR400004", "login ID already exists");
	}
}
