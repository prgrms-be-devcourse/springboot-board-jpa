package com.devcourse.springbootboard.post.converter;

import org.springframework.stereotype.Component;

import com.devcourse.springbootboard.post.domain.Post;
import com.devcourse.springbootboard.post.dto.PostResponse;
import com.devcourse.springbootboard.post.dto.PostWriteRequest;
import com.devcourse.springbootboard.user.domain.User;

@Component
public class PostConverter {
	public PostResponse toPostResponse(Post post) {
		return PostResponse.builder()
			.postId(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.userId(post.getUser().getId())
			.build();
	}

	public Post convertPostWriteRequest(PostWriteRequest postWriteRequest, User user) {
		return Post.builder()
			.title(postWriteRequest.getTitle())
			.content(postWriteRequest.getContent())
			.user(user)
			.build();
	}
}
