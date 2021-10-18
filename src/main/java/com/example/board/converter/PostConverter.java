package com.example.board.converter;

import com.example.board.domain.Post;
import com.example.board.dto.PostDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostConverter {
    public Post convertFromDtoToPost(PostDto postDto) {
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();


        return post;
    }

    public PostDto convertFromPostToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
