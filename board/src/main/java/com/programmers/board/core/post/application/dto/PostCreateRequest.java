package com.programmers.board.core.post.application.dto;

import com.programmers.board.core.post.domain.Post;
import com.programmers.board.core.user.application.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CreateRequestPost {

    private Long userId;

    private String title;

    private String content;

    public Post toEntity() {
        Post post = Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
        return post;
    }

    //Getter


    //Builder
}
