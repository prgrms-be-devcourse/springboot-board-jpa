package com.kdt.jpa.domain.post.dto;

import java.time.LocalDateTime;

import com.kdt.jpa.domain.member.dto.MemberResponse;

public record PostResponse(Long id, String title, String content, MemberResponse author, LocalDateTime createdAt) {
	public PostResponse(Long id, String title, String content, MemberResponse author, LocalDateTime createdAt) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.createdAt = createdAt;
	}

	public record WritePostResponse(Long id) {

	}

	public record UpdatePostResponse(String title, String content) {

	}
}

