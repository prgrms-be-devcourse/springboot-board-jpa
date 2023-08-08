package com.programmers.heheboard.global.response;

import java.util.List;

import org.springframework.data.domain.Slice;

import com.programmers.heheboard.domain.post.PostResponseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiSliceResponse {
	private final int size;
	private final int number;
	private final boolean first;
	private final boolean last;
	private final List<PostResponseDto> content;

	@Builder
	public ApiSliceResponse(int size, int number, boolean first, boolean last,
		List<PostResponseDto> content) {
		this.size = size;
		this.number = number;
		this.first = first;
		this.last = last;
		this.content = content;
	}

	public static ApiResponse<ApiSliceResponse> ok(Slice<PostResponseDto> postSliceResponseDtos) {
		ApiSliceResponse apiSliceResponse = getApiSliceResponse(postSliceResponseDtos);
		return ApiResponse.ok(apiSliceResponse);
	}

	public static ApiResponse<ApiSliceResponse> fail(int statusCode, Slice<PostResponseDto> postSliceResponseDtos) {
		ApiSliceResponse apiSliceResponse = getApiSliceResponse(postSliceResponseDtos);
		return ApiResponse.fail(statusCode, apiSliceResponse);
	}

	private static ApiSliceResponse getApiSliceResponse(Slice<PostResponseDto> postSliceResponseDtos) {
		return ApiSliceResponse.builder()
			.size(postSliceResponseDtos.getSize())
			.number(postSliceResponseDtos.getNumber())
			.first(postSliceResponseDtos.isFirst())
			.last(postSliceResponseDtos.isLast())
			.content(postSliceResponseDtos.getContent())
			.build();
	}
}