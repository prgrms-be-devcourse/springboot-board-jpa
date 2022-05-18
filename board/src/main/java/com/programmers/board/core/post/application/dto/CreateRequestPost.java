package com.programmers.board.core.post.application.dto;

import com.programmers.board.core.post.domain.Post;
import com.programmers.board.core.user.application.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CreateRequestPost {

    private String title;

    private String content;

    private UserDto userDto;

    public Post toEntity() {
        Post post = Post.builder()
                .title(this.title)
                .content(this.title)
                .build();
        post.setUser(userDto.toEntity());
        return post;
    }
}
