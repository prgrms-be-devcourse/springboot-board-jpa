package com.devcourse.springbootboard.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {
	private final Long postId;
	private final String title;
	private final String content;
	private final Long userId;

	@Builder
	public PostResponse(Long postId, String title, String content, Long userId) {
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.userId = userId;
	}
}
