package com.programmers.heheboard.domain.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePostRequestDto {
	private String title;
	private String content;
	private Long userId;

	public Post toEntity() {
		return Post.builder()
			.title(title)
			.content(content)
			.build();
	}
}
