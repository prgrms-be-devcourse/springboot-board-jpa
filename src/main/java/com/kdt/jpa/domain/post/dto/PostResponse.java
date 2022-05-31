package com.kdt.jpa.domain.post.dto;

import java.time.LocalDateTime;

import com.kdt.jpa.domain.member.dto.MemberResponse;

public class PostResponse {
	private Long id;
	private String title;
	private String content;
	private MemberResponse author;

	private LocalDateTime createdAt;

	protected PostResponse() {
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public MemberResponse getAuthor() {
		return author;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public PostResponse(Long id, String title, String content, MemberResponse author, LocalDateTime createdAt) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.createdAt = createdAt;
	}

	public static class WritePostResponse {
		private Long id;

		public Long getId() {
			return id;
		}

		protected WritePostResponse() {

		}

		public WritePostResponse(Long id) {
			this.id = id;
		}
	}

	public static class UpdatePostResponse {
		private String title;
		private String content;

		public String getTitle() {
			return title;
		}

		public String getContent() {
			return content;
		}

		public UpdatePostResponse(String title, String content) {
			this.title = title;
			this.content = content;
		}
	}
}
