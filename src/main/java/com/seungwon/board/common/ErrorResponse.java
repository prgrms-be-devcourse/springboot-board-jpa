package com.seungwon.board.common;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Builder;

public record ErrorResponse(
		LocalDateTime timestamp,
		HttpStatus status,
		int code,
		String message
) {
	@Builder
	public ErrorResponse(HttpStatus status, String message) {
		this(
				LocalDateTime.now(),
				status,
				status.value(),
				message
		);
	}
}
