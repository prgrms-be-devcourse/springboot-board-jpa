package com.eunu.springbootboard.dao.post;

import com.eunu.springbootboard.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post convertPost(PostDto postDto) {
        Post post = new Post(postDto.getId(), postDto.getTitle(), postDto.getContent(),
            postDto.getCreatedAt(), postDto.getCreatedBy());
        return post;
    }

    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .createdBy(post.getCreatedBy())
            .createdAt(post.getCreatedAt())
            .build();
    }

}
