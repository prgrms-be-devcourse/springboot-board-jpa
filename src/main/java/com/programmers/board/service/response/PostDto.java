package com.programmers.board.service.response;

import com.programmers.board.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostDto {
    private final Long postId;
    private final String title;
    private final String content;
    private final UserDto user;

    public static PostDto from(Post post) {
        UserDto userDto = UserDto.from(post.getUser());
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                userDto);
    }
}
