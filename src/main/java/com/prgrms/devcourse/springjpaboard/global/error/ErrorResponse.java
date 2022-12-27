package com.prgrms.devcourse.springjpaboard.global.error;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

	private String message;

	@Builder
	public ErrorResponse(String message) {
		this.message = message;
	}
}
