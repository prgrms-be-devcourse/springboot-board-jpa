package com.prgrms.board.domain.post.dto.response;

import com.prgrms.board.domain.post.entity.Post;

public record PostResponse(
	Long postId,
	String title
) {
	public static PostResponse from(Post post) {
		return new PostResponse(post.getId(), post.getTitle());
	}
}
