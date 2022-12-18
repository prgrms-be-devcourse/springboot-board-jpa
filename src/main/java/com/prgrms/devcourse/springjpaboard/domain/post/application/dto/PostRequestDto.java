package com.prgrms.devcourse.springjpaboard.domain.post.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {

	private Long cursorId;

	private Integer size;

	@Builder
	public PostRequestDto(Long cursorId, Integer size) {
		this.cursorId = cursorId;
		this.size = size;
	}
}
