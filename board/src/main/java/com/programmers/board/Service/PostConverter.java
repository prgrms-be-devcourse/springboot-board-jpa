package com.programmers.board.Service;

import org.springframework.stereotype.Component;

import com.programmers.board.controller.PostDto;
import com.programmers.board.domain.Post;

@Component
public class PostConverter {

	public Post toDomain(PostDto.Save saveDto) {
		return Post.builder()
				.title(saveDto.title())
				.content(saveDto.content())
				.build();
	}

	public PostDto.Response toResponse(Post post) {
		return PostDto.Response.builder()
				.title(post.getTitle())
				.writer(post.getCustomer().getName())
				.content(post.getContent())
				.createdAt(post.getCreatedAt())
				.updatedAt(post.getUpdatedAt())
				.createdBy(post.getCreatedBy())
				.updatedBy(post.getUpdatedBy())
				.build();
	}
}
