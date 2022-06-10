package com.prgrms.boardjpa.application.post;

import javax.validation.constraints.NotBlank;

public class PostDto {
	public record CreatePostRequest(@NotBlank String title,
									Long writerId,
									@NotBlank String content) {
	}

	public record UpdatePostRequest(Long id, @NotBlank String title, @NotBlank String content) {
	}

	public record PostInfo(String title, String content, String writerName, int likeCount) {
	}
}
