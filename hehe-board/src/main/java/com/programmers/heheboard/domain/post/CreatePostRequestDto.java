package com.programmers.heheboard.domain.post;

public record CreatePostRequestDto(String title, String content, Long userId) {

	public Post toEntity() {
		return Post.builder()
			.title(title)
			.content(content)
			.build();
	}
}
