package org.programmers.kdtboard.converter;

import org.programmers.kdtboard.domain.post.Post;
import org.programmers.kdtboard.dto.PostDto.Response;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Response convertPostResponse(Post post) {
        return Response.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy())
                .userId(post.getUser().getId())
                .build();
    }
}
