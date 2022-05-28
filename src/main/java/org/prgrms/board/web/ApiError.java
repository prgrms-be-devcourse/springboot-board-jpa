package org.prgrms.board.web;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ApiError {

	private final LocalDateTime timestamp;
	private final String message;

	public ApiError(LocalDateTime timestamp, String message) {
		this.timestamp = timestamp;
		this.message = message;
	}

	public ApiError(LocalDateTime timestamp, Throwable throwable) {
		this(timestamp, throwable.getMessage());
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("timestamp", timestamp)
			.append("message", message)
			.toString();
	}
}