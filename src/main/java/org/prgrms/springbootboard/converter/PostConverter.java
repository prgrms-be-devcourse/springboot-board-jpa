package org.prgrms.springbootboard.converter;

import org.prgrms.springbootboard.domain.Post;
import org.prgrms.springbootboard.domain.User;
import org.prgrms.springbootboard.dto.PostCreateRequest;
import org.prgrms.springbootboard.dto.PostResponse;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public static Post convertCreateRequestToEntity(PostCreateRequest request, User writer) {
        return Post.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .writer(writer)
            .build();
    }

    public static PostResponse convertEntityToResponse(Post post) {
        return PostResponse.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .createdAt(post.getCreatedAt().toString())
            .lastModifiedAt(post.getLastModifiedAt().toString())
            .build();
    }
}
