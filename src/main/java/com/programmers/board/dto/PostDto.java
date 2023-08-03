package com.programmers.board.dto;

import com.programmers.board.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostDto {
    private Long postId;
    private String title;
    private String content;
    private UserDto user;

    public static PostDto from(Post post) {
        UserDto userDto = UserDto.from(post.getUser());
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                userDto);
    }
}
