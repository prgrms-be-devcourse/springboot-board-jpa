package com.devcourse.springbootboard.post.dto;

import lombok.Getter;

@Getter
public class PostUpdateRequest {
	private Long postId;
	private String title;
	private String content;
	private Long userId;
}
