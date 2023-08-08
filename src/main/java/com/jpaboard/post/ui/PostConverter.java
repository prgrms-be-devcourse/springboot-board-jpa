package com.jpaboard.post.ui;

import com.jpaboard.post.domain.Post;
import com.jpaboard.post.ui.dto.PostRequest;
import com.jpaboard.post.ui.dto.PostResponse;
import com.jpaboard.user.domain.User;
import com.jpaboard.user.ui.UserConverter;
import com.jpaboard.user.ui.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public static Post convertPostRequest(PostRequest postRequest) {
        User user = UserConverter.convertUser(postRequest.user());

        return Post.builder()
                .title(postRequest.title())
                .content(postRequest.content())
                .user(user)
                .build();
    }

    public static Post convertPostResponse(PostResponse postResponse) {
        User user = UserConverter.convertUser(postResponse.user());

        return Post.builder()
                .title(postResponse.title())
                .content(postResponse.content())
                .user(user)
                .build();
    }

    public static PostRequest convertPostRequest(Post post) {
        UserResponse user = UserConverter.convertUserDto(post.getUser());

        return PostRequest.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .user(user)
                .build();
    }

    public static PostResponse convertPostResponse(Post post) {
        UserResponse user = UserConverter.convertUserDto(post.getUser());

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .user(user)
                .build();
    }
}
