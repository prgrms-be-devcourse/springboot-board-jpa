package com.jpa.board.post;

import com.jpa.board.post.dto.PostCreateDto;
import com.jpa.board.post.dto.PostReadDto;
import com.jpa.board.user.User;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {//naming AtoB (A->B 로 convert)

    public PostReadDto postToPostReadDto(Post post) {
        return PostReadDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy())
                .build();
    }

    public Post postCreateDtoToPost(PostCreateDto postCreateDto, User user) {
        return Post.builder()
                .title(postCreateDto.getTitle())
                .content(postCreateDto.getContent())
                .user(user)
                .build();
    }

}
