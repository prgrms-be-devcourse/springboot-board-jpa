package org.prgrms.board.web;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ApiResponse<T> {

	private final boolean success;
	private final T response;
	private final ApiError error;

	public ApiResponse(boolean success, T response, ApiError error) {
		this.success = success;
		this.response = response;
		this.error = error;
	}

	public static <T> ApiResponse<T> ok(T response) {
		return new ApiResponse<>(true, response, null);
	}

	public static ApiResponse<?> error(LocalDateTime timestamp, Throwable throwable) {
		return new ApiResponse<>(false, null, new ApiError(timestamp, throwable));
	}

	public boolean isSuccess() {
		return success;
	}

	public T getResponse() {
		return response;
	}

	public ApiError getError() {
		return error;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("success", success)
			.append("response", response)
			.append("error", error)
			.toString();
	}
}