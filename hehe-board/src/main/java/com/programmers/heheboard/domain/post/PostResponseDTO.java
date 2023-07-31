package com.programmers.heheboard.domain.post;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponseDTO {
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	@Builder
	public PostResponseDTO(String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	public static PostResponseDTO toResponse(Post post) {
		return PostResponseDTO.builder()
			.title(post.getTitle())
			.content(post.getContent())
			.createdAt(post.getCreatedAt())
			.modifiedAt(post.getModifiedAt())
			.build();
	}
}
