package com.programmers.springboard.global.common;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
	private final String statusCode;
	private final T data;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime serverDatetime;

	public ApiResponse(String statusCode, T data) {
		this.statusCode = statusCode;
		this.data = data;
		this.serverDatetime = LocalDateTime.now();
	}

	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>("200OK", data);
	}

	public static ApiResponse<Void> noContent() {
		return new ApiResponse<>("204NOCONTENT", null);
	}

	public static <T> ApiResponse<T> created(T data) {
		return new ApiResponse<>("201CREATED", data);
	}
}
