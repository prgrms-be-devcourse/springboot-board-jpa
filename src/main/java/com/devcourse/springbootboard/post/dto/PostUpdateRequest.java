package com.devcourse.springbootboard.post.dto;

import lombok.Getter;

@Getter
public class PostUpdateRequest {
	private Long postId;
	private String title;
	private String content;
	private Long userId;

	public PostUpdateRequest(Long postId, String title, String content, Long userId) {
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.userId = userId;
	}
}
