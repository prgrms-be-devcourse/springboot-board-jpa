package com.seungwon.board.common.exception;

public class InvalidDataException extends RuntimeException {
	public InvalidDataException() {
	}

	public InvalidDataException(String message) {
		super(message);
	}
}
