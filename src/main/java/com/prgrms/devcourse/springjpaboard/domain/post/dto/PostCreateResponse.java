package com.prgrms.devcourse.springjpaboard.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateResponse {

	private Long id;

	@Builder
	public PostCreateResponse(Long id) {
		this.id = id;
	}
}
