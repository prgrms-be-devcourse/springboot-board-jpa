package com.devcourse.springbootboard.post.converter;

import com.devcourse.springbootboard.post.domain.Post;
import com.devcourse.springbootboard.post.dto.PostResponse;

public class PostConverter {
	private PostConverter() {

	}

	public static PostResponse toPostResponse(Post post) {
		return PostResponse.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.name(post.getUser().getName())
			.build();
	}
}
