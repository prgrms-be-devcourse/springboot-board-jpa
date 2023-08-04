package com.seungwon.board.post.application.dto;

import java.time.LocalDateTime;

import com.seungwon.board.post.domain.Post;

public record PostSaveRequestDto(
		Long id,
		LocalDateTime updatedAt
) {
	public PostSaveRequestDto(Post post) {
		this(post.getId(), post.getUpdatedAt());
	}
}
