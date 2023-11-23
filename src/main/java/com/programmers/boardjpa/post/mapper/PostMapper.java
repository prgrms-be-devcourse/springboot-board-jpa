package com.programmers.boardjpa.post.mapper;

import com.programmers.boardjpa.post.dto.PostInsertRequestDto;
import com.programmers.boardjpa.post.dto.PostResponseDto;
import com.programmers.boardjpa.post.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostResponseDto toPostResponseDto(Post post) {
        return PostResponseDto.builder()
                .postId(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .title(post.getTitle())
                .userId(post.getUser().getId())
                .build();
    }

    public Post postInsertRequestDtoToPost(PostInsertRequestDto postInsertRequestDto) {
        return Post.builder()
                .title(postInsertRequestDto.title())
                .content(postInsertRequestDto.content())
                .build();
    }
}
