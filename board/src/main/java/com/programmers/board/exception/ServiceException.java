package com.programmers.board.exception;

public class ServiceException {

	public static class NotFoundResource extends RuntimeException{
		public NotFoundResource(String message) {
			super(message);
		}
	}
}
