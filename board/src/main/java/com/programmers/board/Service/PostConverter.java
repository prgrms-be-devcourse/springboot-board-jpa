package com.programmers.board.Service;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.programmers.board.controller.PageResponseDto;
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

	public Post toDomain(PostDto.Update updateDto) {
		return Post.builder()
				.title(updateDto.title())
				.content(updateDto.content())
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

	public PageResponseDto<PostDto.Response, Post> toResponse(Page<Post> pagingPosts) {
		Function<Post, PostDto.Response> function = this::toResponse;
		return new PageResponseDto<>(pagingPosts, function);
	}
}
