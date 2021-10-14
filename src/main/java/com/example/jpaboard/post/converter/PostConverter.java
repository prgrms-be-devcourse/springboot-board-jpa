package com.example.jpaboard.post.converter;

import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.dto.PostResponse;

public class PostConverter {

    public static PostResponse convertPostResponse(Post post) {
        Long id = post.getId();
        String title = post.getTitle();
        String content = post.getContent();
        String author = post.getAuthor().getName();

        return new PostResponse(id, title, content, author);
    }
}
