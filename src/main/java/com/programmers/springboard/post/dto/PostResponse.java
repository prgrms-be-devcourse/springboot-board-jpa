package com.programmers.springboard.post.dto;

import java.time.LocalDateTime;

import com.programmers.springboard.post.domain.Post;

public record PostResponse(Long postId, String title, String content, Long memberId, String memberName, LocalDateTime createdAt) {
	public static PostResponse of(Post post) {
		return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getMember().getId(), post.getMember().getName(), post.getCreatedAt());
	}
}
