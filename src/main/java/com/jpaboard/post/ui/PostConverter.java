package com.jpaboard.post.ui;

import com.jpaboard.post.domain.Post;
import com.jpaboard.post.ui.dto.PostResponse;
import com.jpaboard.user.domain.User;
import com.jpaboard.user.ui.UserConverter;
import com.jpaboard.user.ui.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public static Post convertPost(PostResponse postResponse) {
        User user = UserConverter.convertUser(postResponse.user());

        return Post.builder()
                .title(postResponse.title())
                .content(postResponse.content())
                .user(user)
                .build();
    }

    public static PostResponse convertPostDto(Post post) {
        UserResponse userResponse = UserConverter.convertUserDto(post.getUser());

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .user(userResponse)
                .build();
    }

}
