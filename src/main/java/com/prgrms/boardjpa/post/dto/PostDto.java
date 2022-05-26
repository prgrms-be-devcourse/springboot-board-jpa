package com.prgrms.boardjpa.post.dto;

import javax.validation.constraints.NotBlank;

import com.prgrms.boardjpa.post.Post;
import com.prgrms.boardjpa.user.User;

public class PostDto {
	public record CreateRequest(@NotBlank String title,
								Long writerId,
								@NotBlank String content) {

		public Post createPost(User writer) {
			return Post.builder()
				.writer(writer)
				.content(this.content)
				.title(this.title)
				.build();
		}
	}

	public record UpdateRequest(Long id, @NotBlank String title, @NotBlank String content) {
	}

	public record Info(String title, String content, String writerName) {
		public static Info from(Post post) {
			return new Info(post.getTitle(),
				post.getContent(),
				post.getCreatedBy());
		}
	}
}
