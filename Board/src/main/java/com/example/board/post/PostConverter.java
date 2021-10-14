package com.example.board.post;

import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public static Post of(PostDto postDto) {
        Post post = new Post(postDto.title(), postDto.content());
        return post;
    }

    public PostDto toPostDto(Post post){
        PostDto postDto = new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getMember().getName());
        return postDto;
    }
}
