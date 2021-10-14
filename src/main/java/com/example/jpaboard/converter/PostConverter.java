package com.example.jpaboard.converter;

import com.example.jpaboard.domain.Post;
import com.example.jpaboard.dto.PostResponse;

public class PostConverter {

    public static PostResponse convertPostResponse(Post post) {
        Long id = post.getId();
        String title = post.getTitle();
        String content = post.getContent();
        String author = post.getAuthor().getName();

        return new PostResponse(id, title, content, author);
    }
}
