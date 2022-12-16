package com.prgrms.be.app.util;

import com.prgrms.be.app.domain.Post;
import com.prgrms.be.app.domain.User;
import com.prgrms.be.app.domain.dto.PostDTO;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post covertToPost(PostDTO.CreateRequest postCreateDto, User user) {
        return new Post(postCreateDto.title(), postCreateDto.content(), user);
    }

    public PostDTO.PostsResponse convertToPostsResponse(Post post) {
        return new PostDTO.PostsResponse(
                post.getTitle(),
                post.getId(),
                post.getCreatedAt()
        );
    }

    public PostDTO.PostDetailResponse convertToPostDetailResponse(Post post) {
        return new PostDTO.PostDetailResponse(
                post.getTitle(),
                post.getContent(),
                post.getId(),
                post.getCreatedAt(),
                post.getCreatedBy().getId(),
                post.getCreatedBy().getName()
        );
    }
}
