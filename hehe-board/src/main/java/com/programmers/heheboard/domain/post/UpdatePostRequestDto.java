package com.programmers.heheboard.domain.post;

import jakarta.validation.constraints.NotBlank;

public final class UpdatePostRequestDto {
	@NotBlank
	private final String title;

	@NotBlank
	private final String content;

	public UpdatePostRequestDto(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

}
