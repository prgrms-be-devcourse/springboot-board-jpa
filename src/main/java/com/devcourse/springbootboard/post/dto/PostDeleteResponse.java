package com.devcourse.springbootboard.post.dto;

import lombok.Getter;

@Getter
public class PostDeleteResponse {
	private final Long id;

	public PostDeleteResponse(Long id) {
		this.id = id;
	}
}
