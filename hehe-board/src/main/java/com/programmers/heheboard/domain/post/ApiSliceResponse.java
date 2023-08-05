package com.programmers.heheboard.domain.post;

import java.util.List;

import org.springframework.data.domain.Slice;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiSliceResponse {
	private final int statusCode;
	private final int size;
	private final int number;
	private final boolean first;
	private final boolean last;
	private final List<PostResponseDto> content;

	@Builder
	public ApiSliceResponse(int statusCode, int size, int number, boolean first, boolean last,
		List<PostResponseDto> content) {
		this.statusCode = statusCode;
		this.size = size;
		this.number = number;
		this.first = first;
		this.last = last;
		this.content = content;
	}

	public static ApiSliceResponse ok(Slice<PostResponseDto> postSliceResponseDtos) {
		return ApiSliceResponse.builder()
			.statusCode(200)
			.size(postSliceResponseDtos.getSize())
			.number(postSliceResponseDtos.getNumber())
			.first(postSliceResponseDtos.isFirst())
			.last(postSliceResponseDtos.isLast())
			.content(postSliceResponseDtos.getContent())
			.build();
	}

	public static ApiSliceResponse fail(int statusCode, Slice<PostResponseDto> postSliceResponseDtos) {
		return ApiSliceResponse.builder()
			.statusCode(statusCode)
			.size(postSliceResponseDtos.getSize())
			.number(postSliceResponseDtos.getNumber())
			.first(postSliceResponseDtos.isFirst())
			.last(postSliceResponseDtos.isLast())
			.content(postSliceResponseDtos.getContent())
			.build();
	}
}