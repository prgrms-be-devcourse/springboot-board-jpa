package com.devcourse.springbootboard.post.converter;

import com.devcourse.springbootboard.post.domain.Post;
import com.devcourse.springbootboard.post.dto.PostResponse;
import com.devcourse.springbootboard.post.dto.PostWriteRequest;
import com.devcourse.springbootboard.user.domain.User;

public class PostConverter {
	private PostConverter() {
	}

	public static PostResponse toPostResponse(Post post) {
		return PostResponse.builder()
			.postId(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.userId(post.getUser().getId())
			.build();
	}

	public static Post convertPostWriteRequest(PostWriteRequest postWriteRequest, User user) {
		return Post.builder()
			.title(postWriteRequest.getTitle())
			.content(postWriteRequest.getContent())
			.user(user)
			.build();
	}
}
