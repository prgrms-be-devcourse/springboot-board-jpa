package com.example.yiseul.converter;

import com.example.yiseul.domain.Member;
import com.example.yiseul.domain.Post;
import com.example.yiseul.dto.post.PostCreateRequestDto;
import com.example.yiseul.dto.post.PostPageResponseDto;
import com.example.yiseul.dto.post.PostResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static Post convertPost(
            Member member,
            PostCreateRequestDto createRequestDto
    ){

        return new Post(
                member,
                createRequestDto.title(),
                createRequestDto.content()
        );
    }

    public static PostResponseDto convertPostResponseDto(Post post){

        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getCreatedBy()
        );
    }

    public static PostPageResponseDto convertPostPageResponseDto(Page<Post> page) {
        List<PostResponseDto> postResponseDtos = page.getContent().stream()
                .map(post -> convertPostResponseDto(post))
                .collect(Collectors.toList());

        return new PostPageResponseDto(
                postResponseDtos,
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast());
    }
}
