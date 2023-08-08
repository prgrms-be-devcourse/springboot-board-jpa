package com.programmers.heheboard.domain.post;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record PostResponseDto(String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
	public static PostResponseDto toResponse(Post post) {
		return PostResponseDto.builder()
			.title(post.getTitle())
			.content(post.getContent())
			.createdAt(post.getCreatedAt())
			.modifiedAt(post.getModifiedAt())
			.build();
	}
}
