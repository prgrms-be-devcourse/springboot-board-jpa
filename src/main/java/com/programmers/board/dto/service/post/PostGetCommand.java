package com.programmers.board.dto.service.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostGetCommand {
    private final Long postId;

    public static PostGetCommand of(Long postId) {
        return new PostGetCommand(postId);
    }
}
