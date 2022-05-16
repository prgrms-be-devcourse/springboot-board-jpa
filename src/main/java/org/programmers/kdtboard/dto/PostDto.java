package org.programmers.kdtboard.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostDto {

	public record PostResponse(
		Long id,
		String title,
		String content,
		LocalDateTime createdAt,
		String createdBy,
		Long userId) {
	}

	public record PostCreateRequest(

		@NotBlank @Size(max = 30)
		String title,
		@NotBlank @Size
		String content,
		@NotNull
		Long userId) {
	}

	public record PostUpdateRequest(

		@NotBlank @Size(max = 30)
		String title,
		@NotBlank @Size
		String content) {
	}
}
