package com.example.springbootboard.converter;

import com.example.springbootboard.domain.Post;
import com.example.springbootboard.dto.PostDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public PostDto convertPost(Post post) {
        return new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getUser());
    }
}
