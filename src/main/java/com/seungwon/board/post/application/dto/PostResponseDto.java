package com.seungwon.board.post.application.dto;

import java.time.LocalDateTime;

import com.seungwon.board.post.domain.Post;

public record PostResponseDto(
		Long id,
		String content,
		String title,
		Long writerId,
		LocalDateTime updatedAt

) {
	public PostResponseDto(Post post) {
		this(post.getId(),
				post.getContent(),
				post.getTitle(),
				post.getWriter().getId(),
				post.getUpdatedAt()
		);
	}
}
