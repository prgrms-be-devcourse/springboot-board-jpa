package com.prgrms.be.app.util;

import com.prgrms.be.app.domain.Post;
import com.prgrms.be.app.domain.User;
import com.prgrms.be.app.domain.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PostConverter {
    public Post convertToPost(PostDTO.CreateRequest postCreateDto, User user) {
        return new Post(
                postCreateDto.getTitle(),
                postCreateDto.getContent(),
                user);
    }

    public PostDTO.PostsResponse convertToPostsResponse(Page<Post> allPost) {
        return new PostDTO.PostsResponse(
                allPost.getContent().stream().map(this::convertToPostDetailResponse).collect(Collectors.toList()),
                allPost.getTotalPages(),
                allPost.hasNext()
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
