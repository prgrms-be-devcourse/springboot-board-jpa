package com.prgrms.devcourse.springjpaboard.domain.post.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSearchResponses {

	private List<PostSearchResponse> posts;

	private Boolean hasNext;

	@Builder
	public PostSearchResponses(List<PostSearchResponse> posts, Boolean hasNext) {
		this.posts = posts;
		this.hasNext = hasNext;
	}
}
