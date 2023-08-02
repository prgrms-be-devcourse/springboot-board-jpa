package com.jpaboard.post.ui;

import com.jpaboard.post.domain.Post;
import com.jpaboard.post.ui.dto.PostDto;
import com.jpaboard.user.ui.UserConverter;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public static Post convertPost(PostDto postDto) {
        return Post.builder()
                .title(postDto.title())
                .content(postDto.content())
                .user(UserConverter.convertUser(postDto.user()))
                .build();
    }

    public static PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .user(UserConverter.convertUserDto(post.getUser()))
                .build();
    }

}
