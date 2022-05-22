package org.programmers.kdtboard.converter;

import org.programmers.kdtboard.domain.post.Post;
import org.programmers.kdtboard.dto.PostDto.PostResponseDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
	public PostResponseDto convertPostResponse(Post post) {
		return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(),
			post.getCreatedAt(), post.getCreatedBy(), post.getUser().getId());
	}
}
