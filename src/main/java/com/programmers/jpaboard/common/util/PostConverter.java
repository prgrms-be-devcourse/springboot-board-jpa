package com.programmers.jpaboard.common.util;

import com.programmers.jpaboard.domain.post.entity.Post;
import com.programmers.jpaboard.domain.user.entity.User;
import com.programmers.jpaboard.web.post.dto.PostCreateRequestDto;
import com.programmers.jpaboard.web.post.dto.PostResponseDto;

public class PostConverter {

	public static Post toPost(PostCreateRequestDto postCreateRequestDto, User user) {
		return new Post(postCreateRequestDto.getTitle(), postCreateRequestDto.getContent(), user);
	}

	public static PostResponseDto toPostResponseDto(Post post) {
		return new PostResponseDto(
			post.getId(),
			post.getTitle(),
			post.getContent(),
			post.getUser().getId(),
			post.getCreatedAt(),
			post.getLastModifiedAt()
		);
	}
}
