package com.programmers.springboard.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
	private final int statusCode;
	private final T data;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime serverDatetime;

	public ApiResponse(int statusCode, T data) {
		this.statusCode = statusCode;
		this.data = data;
		this.serverDatetime = LocalDateTime.now();
	}

	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>(200, data);
	}

	public static ApiResponse<Void> noContent() {
		return new ApiResponse<>(204, null);
	}

}
