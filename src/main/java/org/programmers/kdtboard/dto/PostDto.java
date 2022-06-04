package org.programmers.kdtboard.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostDto {

	public record Response(
		Long id,
		String title,
		String content,
		LocalDateTime createdAt,
		String createdBy,
		Long userId) {
	}

	public record CreateRequest(
		@NotBlank @Size(max = 30)
		String title,
		@NotEmpty
		String content,
		@NotNull
		Long userId) {
	}

	public record UpdateRequest(
		@NotBlank @Size(max = 30)
		String title,
		@NotEmpty
		String content) {
	}
}
