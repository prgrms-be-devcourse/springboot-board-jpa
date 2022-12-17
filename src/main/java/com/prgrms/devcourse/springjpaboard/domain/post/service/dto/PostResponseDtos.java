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

	private Long cursorId;

	private Boolean hasNext;

	@Builder
	public PostResponseDtos(
		List<PostResponseDto> postResponseDtoList, Long cursorId, boolean hasNext) {
		this.postResponseDtoList = postResponseDtoList;
		this.cursorId = cursorId;
		this.hasNext = hasNext;
	}
}
