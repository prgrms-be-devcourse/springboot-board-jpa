package com.example.jpaboard.post.converter;

import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.dto.PostDto;
import com.example.jpaboard.post.dto.PostResponse;
import com.example.jpaboard.user.converter.UserConverter;
import com.example.jpaboard.user.dto.UserDto;

public class PostConverter {

    public static PostResponse convertPostResponse(Post post) {
        Long id = post.getId();
        String title = post.getTitle();
        String content = post.getContent();
        String author = post.getAuthor().getName();

        return new PostResponse(id, title, content, author);
    }

    public static PostDto convertPostDto(Post post) {
        Long id = post.getId();
        String title = post.getTitle();
        String content = post.getContent();
        UserDto author = UserConverter.convertUserDto(post.getAuthor());
        return new PostDto(id, title, content, author);
    }
}
