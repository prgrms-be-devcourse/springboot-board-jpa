package com.programmers.board.controller;

import java.time.LocalDateTime;

import lombok.Builder;

public class PostDto {

	@Builder
	public record Save(
			String title,
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
