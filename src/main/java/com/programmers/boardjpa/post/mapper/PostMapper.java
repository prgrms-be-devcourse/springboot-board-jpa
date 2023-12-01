package com.programmers.boardjpa.post.mapper;

import com.programmers.boardjpa.post.dto.PostInsertRequestDto;
import com.programmers.boardjpa.post.dto.PostPageResponseDto;
import com.programmers.boardjpa.post.dto.PostResponseDto;
import com.programmers.boardjpa.post.entity.Post;
import com.programmers.boardjpa.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public class PostMapper {

    public static PostResponseDto toPostResponseDto(Post post) {
        return PostResponseDto.builder()
                .postId(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .title(post.getTitle())
                .userId(post.getUser().getId())
                .build();
    }

    public static PostPageResponseDto toPostPageResponseDto(Page<Post> postList) {
        List<PostResponseDto> data = postList.map(PostMapper::toPostResponseDto).toList();

        return PostPageResponseDto.builder()
                .data(data)
                .page(postList.getTotalPages())
                .size(postList.getSize())
                .build();
    }

    public static Post postInsertRequestDtoToPost(PostInsertRequestDto postInsertRequestDto, User user) {
        return Post.builder()
                .title(postInsertRequestDto.title())
                .content(postInsertRequestDto.content())
                .user(user)
                .build();
    }
}
