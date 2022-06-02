package com.prgrms.boardjpa.application.post;

import javax.validation.constraints.NotBlank;

import com.prgrms.boardjpa.application.post.model.Post;
import com.prgrms.boardjpa.application.user.model.User;

public class PostDto {
	public record CreatePostRequest(@NotBlank String title,
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

	public record UpdatePostRequest(Long writerId, @NotBlank String title, @NotBlank String content) {
	}

	public record PostInfo(String title, String content, String writerName) {
		public static PostInfo from(Post post) {
			return new PostInfo(post.getTitle(),
				post.getContent(),
				post.getCreatedBy());
		}
	}
}
