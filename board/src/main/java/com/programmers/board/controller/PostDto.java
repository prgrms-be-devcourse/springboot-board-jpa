package com.programmers.board.controller;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Builder;

public class PostDto {

	@Builder
	public record Save(
			@NotBlank(message = "제목 작성이 필요합니다.")
			String title,
			@NotBlank(message = "본문 작성이 필요합니다.")
			String content
	) {

	}

	@Builder
	public record Update(
			Long id,
			@NotBlank(message = "제목 작성이 필요합니다.")
			String title,
			@NotBlank(message = "본문 작성이 필요합니다.")
			String content
	) {

	}

	@Builder
	public record Response(
			Long id,
			String title,
			String writer,
			String content,
			LocalDateTime createdAt,
			LocalDateTime updatedAt,
			String createdBy,
			String updatedBy
	) {

	}
}
