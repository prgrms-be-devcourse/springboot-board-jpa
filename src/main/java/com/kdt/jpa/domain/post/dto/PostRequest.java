package com.kdt.jpa.domain.post.dto;

public class PostRequest {
	public record WritePostRequest(String title, String content, Long authorId) {

	}

	public record UpdatePostRequest(String title, String content) {
	}
}