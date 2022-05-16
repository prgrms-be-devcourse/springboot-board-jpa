package org.programmers.kdtboard.controller.response;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

	private final T data;
	private final HttpStatus httpStatus = HttpStatus.OK;

	private ApiResponse(T data) {
		this.data = data;
	}

	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>(data);
	}

	public T getData() {
		return data;
	}
}
