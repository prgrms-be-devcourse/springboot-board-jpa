package org.programmers.kdtboard.controller.response;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(T data, HttpStatus httpStatus) {

	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>(data, HttpStatus.OK);
	}

	public static <T> ApiResponse<T> create(T data) {
		return new ApiResponse<>(data, HttpStatus.CREATED);
	}
}