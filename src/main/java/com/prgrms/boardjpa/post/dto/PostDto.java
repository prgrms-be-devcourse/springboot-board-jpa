package com.prgrms.boardjpa.post.dto;

import javax.validation.constraints.NotBlank;

import com.prgrms.boardjpa.post.Post;

public class PostDto {
	public record CreateRequest(@NotBlank String title,
								Long writerId,
								@NotBlank String content) {
	}

	public record UpdateRequest(Long id, @NotBlank String title, @NotBlank String content) {
	}

	public record Info(String title, String content, String writerName) {
		public Info(Post post) {
			this(post.getTitle(),
				post.getContent(),
				post.getCreatedBy());
		}
	}
}
