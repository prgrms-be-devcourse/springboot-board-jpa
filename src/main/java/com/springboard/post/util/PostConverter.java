package com.springboard.post.util;

import com.springboard.post.dto.CreatePostRequest;
import com.springboard.post.dto.CreatePostResponse;
import com.springboard.post.dto.FindPostResponse;
import com.springboard.post.entity.Post;
import com.springboard.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post getPostFrom(CreatePostRequest request, User user) {
        return new Post(request.title(), request.content(), user);
    }

    public CreatePostResponse getCreateResponseFrom(Post post) {
        CreatePostResponse.UserResponse userResponse
            = new CreatePostResponse.UserResponse(post.getUser().getId(), post.getUser().getName());
        return new CreatePostResponse(post.getId(), post.getTitle(), post.getContent(), userResponse,
            post.getCreatedAt());
    }

    public FindPostResponse getFindResponseFrom(Post post) {
        FindPostResponse.UserResponse userResponse
            = new FindPostResponse.UserResponse(post.getUser().getId(), post.getUser().getName());
        return new FindPostResponse(post.getId(), post.getTitle(), post.getContent(), userResponse,
            post.getCreatedAt(), post.getUpdatedAt());
    }
}
