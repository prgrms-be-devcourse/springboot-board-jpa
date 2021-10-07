package com.devcourse.springbootboard.global.error;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;

import com.devcourse.springbootboard.global.error.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
	private final String error;
	private final String message;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime serverDateTime;

	@Builder
	public ErrorResponse(String error, String message) {
		this.error = error;
		this.message = message;
		this.serverDateTime = LocalDateTime.now();
	}

	public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
		return ResponseEntity
			.status(errorCode.getHttpStatus())
			.body(ErrorResponse.builder()
				.error(errorCode.getHttpStatus().name())
				.message(errorCode.getMessage())
				.build());
	}
}
