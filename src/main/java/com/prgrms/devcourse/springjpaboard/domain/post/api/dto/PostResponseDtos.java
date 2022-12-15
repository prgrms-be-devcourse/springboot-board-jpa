package com.prgrms.devcourse.springjpaboard.domain.post.api.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDtos {

	private List<PostResponseDto> postResponseDtoList;

	@Builder
	public PostResponseDtos(
		List<PostResponseDto> postResponseDtoList) {
		this.postResponseDtoList = postResponseDtoList;
	}
}
