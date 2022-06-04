package org.programmers.kdtboard.converter;

import org.programmers.kdtboard.domain.post.Post;
import org.programmers.kdtboard.dto.PostDto.Response;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
	public Response convertPostResponse(Post post) {
		return new Response(post.getId(), post.getTitle(), post.getContent(),
			post.getCreatedAt(), post.getCreatedBy(), post.getUser().getId());
	}
}
