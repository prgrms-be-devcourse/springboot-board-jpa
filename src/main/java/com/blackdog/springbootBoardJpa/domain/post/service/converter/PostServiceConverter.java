package com.blackdog.springbootBoardJpa.domain.post.service.converter;

import com.blackdog.springbootBoardJpa.domain.post.model.Post;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostCreateRequest;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostResponse;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostResponses;
import com.blackdog.springbootBoardJpa.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PostServiceConverter {

    public Post toEntity(PostCreateRequest dto, User user) {
        return Post.builder()
                .title(dto.title())
                .content(dto.content())
                .user(user)
                .build();
    }

    public PostResponse toResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getName().getNameValue(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    public PostResponses toResponses(Page<Post> posts) {
        return new PostResponses(posts.map(this::toResponse));
    }
}
