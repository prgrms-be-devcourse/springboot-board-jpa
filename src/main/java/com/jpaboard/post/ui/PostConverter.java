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

        return new Post(request.title(), request.content(), user);
    }

    public static Post convertPostResponse(PostDto.Response response) {
        User user = UserConverter.convertUser(response.user());

        return new Post(response.title(), response.content(), user);
    }

    public static PostDto.Request convertPostRequest(Post post) {
        UserDto.Request user = UserConverter.convertUserRequest(post.getUser());

        return new PostDto.Request(post.getTitle(), post.getContent(), user);
    }

    public static PostDto.Response convertPostResponse(Post post) {
        UserDto.Response user = UserConverter.convertUserResponse(post.getUser());

        return new PostDto.Response(post.getTitle(), post.getContent(), user);
    }
}
