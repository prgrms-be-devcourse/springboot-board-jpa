package com.devcourse.springbootboard.post.dto;

import lombok.Getter;

@Getter
public class PostWriteRequest {
	private String title;
	private String content;
	private Long userId;

	public PostWriteRequest(String title, String content, Long userId) {
		this.title = title;
		this.content = content;
		this.userId = userId;
	}
}
