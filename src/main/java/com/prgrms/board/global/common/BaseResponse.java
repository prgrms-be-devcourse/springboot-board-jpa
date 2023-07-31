package com.prgrms.board.global.common;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;
import static org.springframework.http.HttpStatus.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseResponse<T> {

	private final int status;
	private final String message;

	@JsonInclude(NON_NULL)
	private T data;

	public static BaseResponse<Object> ok(SuccessMessage message) {
		return new BaseResponse<>(OK.value(), message.getValue());
	}

	public static <T> BaseResponse<T> ok(SuccessMessage message, T data) {
		return new BaseResponse<>(OK.value(), message.getValue(), data);
	}
}
