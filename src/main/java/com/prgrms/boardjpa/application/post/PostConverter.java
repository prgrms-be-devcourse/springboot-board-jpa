package com.prgrms.boardjpa.application.post;

import org.springframework.stereotype.Component;

import com.prgrms.boardjpa.application.post.model.Post;
import com.prgrms.boardjpa.application.user.model.User;

@Component
public class PostConverter {

	public Post createRequest2Entity(User writer, PostDto.CreatePostRequest request) {
		return Post.builder()
			.writer(writer)
			.content(request.content())
			.title(request.title())
			.build();
	}

	public PostDto.PostInfo entity2Info(Post post) {
		return new PostDto.PostInfo(post.getTitle(),
			post.getContent(),
			post.getCreatedBy());
	}
}
