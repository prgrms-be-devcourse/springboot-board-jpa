package com.programmers.heheboard.domain.post;

import java.time.LocalDateTime;

import lombok.Builder;

public record PostResponseDto(String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
	@Builder
	public PostResponseDto {
	}

	public static PostResponseDto toResponse(Post post) {
		return PostResponseDto.builder()
			.title(post.getTitle())
			.content(post.getContent())
			.createdAt(post.getCreatedAt())
			.modifiedAt(post.getModifiedAt())
			.build();
	}
}
