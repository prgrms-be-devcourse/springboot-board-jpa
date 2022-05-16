package com.programmers.board.exception;

public class DomainException {
	public static class ConstraintException extends RuntimeException {
		public ConstraintException(String message) {
			super(message);
		}
	}
}
