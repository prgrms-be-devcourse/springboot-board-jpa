package com.eunu.springbootboard.domain.post;

import com.eunu.springbootboard.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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
