package com.programmers.heheboard.domain.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class CreatePostRequestDto {

	@NotBlank
	private final String title;

	@NotBlank
	private final String content;

	private final Long userId;

	public CreatePostRequestDto(String title, String content, Long userId) {
		this.title = title;
		this.content = content;
		this.userId = userId;
	}

	public Post toEntity() {
		return Post.builder()
			.title(title)
			.content(content)
			.build();
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Long getUserId() {
		return userId;
	}
}
