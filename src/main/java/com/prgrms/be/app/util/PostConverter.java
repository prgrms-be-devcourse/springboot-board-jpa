package com.prgrms.be.app.util;

import com.prgrms.be.app.domain.Post;
import com.prgrms.be.app.domain.User;
import com.prgrms.be.app.domain.dto.PostCreateRequest;
import com.prgrms.be.app.domain.dto.PostDetailResponse;
import com.prgrms.be.app.domain.dto.PostsResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PostConverter {
    public Post convertToPost(PostCreateRequest postCreateDto, User user) {
        return new Post(
                postCreateDto.getTitle(),
                postCreateDto.getContent(),
                user);
    }

    public PostsResponse convertToPostsResponse(Page<Post> allPost) {
        return new PostsResponse(
                allPost.getContent().stream()
                        .map(this::convertToPostDetailResponse)
                        .collect(Collectors.toList()),
                allPost.getTotalPages(),
                allPost.hasNext()
        );
    }

    public PostDetailResponse convertToPostDetailResponse(Post post) {
        return new PostDetailResponse(
                post.getTitle(),
                post.getContent(),
                post.getId(),
                post.getCreatedAt(),
                post.getCreatedBy().getId(),
                post.getCreatedBy().getName()
        );
    }
}
