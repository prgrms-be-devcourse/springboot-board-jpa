package com.kdt.jpa.domain.post.dto;

public class PostRequest {

	public static class WritePostRequest {
		private String title;
		private String content;
		private Long authorId;

		public String getTitle() {
			return title;
		}

		public String getContent() {
			return content;
		}

		public Long getAuthorId() {
			return authorId;
		}

		protected WritePostRequest() {
		}

		public WritePostRequest(String title, String content, Long authorId) {
			this.title = title;
			this.content = content;
			this.authorId = authorId;
		}
	}

	public static class UpdatePostRequest {
		private String title;
		private String content;

		public String getTitle() {
			return title;
		}

		public String getContent() {
			return content;
		}

		protected UpdatePostRequest() {

		}
		public UpdatePostRequest(String title, String content) {
			this.title = title;
			this.content = content;
		}
	}
}
