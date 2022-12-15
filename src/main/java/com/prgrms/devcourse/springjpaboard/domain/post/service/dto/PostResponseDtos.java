package com.prgrms.devcourse.springjpaboard.domain.post.service.dto;

import java.util.List;

import lombok.AccessLevel;
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
