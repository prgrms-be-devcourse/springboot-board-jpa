package com.devcourse.springbootboard.post.dto;

import lombok.Builder;

@Builder
public class PostResponse {
	private Long id;
	private String title;
	private String content;
	private String name;
}
