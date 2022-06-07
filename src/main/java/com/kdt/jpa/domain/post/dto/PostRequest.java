package com.kdt.jpa.domain.post.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record PostRequest() {

	public record WritePostRequest(
		@Size(min = 1, max = 50)
		@NotBlank
		String title,

		@NotBlank
		@Size(min = 1, max = 500)
		String content,

		@NotNull
		Long authorId
	) {
	}

	public record UpdatePostRequest(
		@Size(min = 1, max = 50)
		@NotBlank
		String title,

		@NotBlank
		@Size(min = 1, max = 500)
		String content) {
	}
}