package com.jpaboard.post.ui;

import com.jpaboard.post.domain.Post;
import com.jpaboard.post.ui.dto.PostDto;
import com.jpaboard.user.domain.User;
import com.jpaboard.user.ui.UserConverter;
import com.jpaboard.user.ui.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public static Post convertPostRequest(PostDto.Request request) {
        User user = UserConverter.convertUser(request.user());

        return Post.builder()
                .title(request.title())
                .content(request.content())
                .user(user)
                .build();
    }

    public static Post convertPostResponse(PostDto.Response response) {
        User user = UserConverter.convertUser(response.user());

        return Post.builder()
                .title(response.title())
                .content(response.content())
                .user(user)
                .build();
    }

    public static PostDto.Request convertPostRequest(Post post) {
        UserDto.Request user = UserConverter.convertUserRequest(post.getUser());

        return PostDto.Request.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .user(user)
                .build();
    }

    public static PostDto.Response convertPostResponse(Post post) {
        UserDto.Response user = UserConverter.convertUserResponse(post.getUser());

        return PostDto.Response.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .user(user)
                .build();
    }
}
