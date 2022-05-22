package com.kdt.board.post.convert;

import com.kdt.board.post.domain.Post;
import com.kdt.board.post.dto.request.PostCreateRequestDto;
import com.kdt.board.post.dto.response.PostResponseDto;
import com.kdt.board.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    //dto -> entity
    public Post toPost (PostCreateRequestDto requestDto, User user) {
        Post post = new Post(requestDto.getTitle(), requestDto.getContent());
        post.setUser(user);
        return post;
    }

    //entity -> dto
    public PostResponseDto toPostResponseDto (Post post) {
        return PostResponseDto.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .userId(post.getUser().getId())
            .build();
    }
}
