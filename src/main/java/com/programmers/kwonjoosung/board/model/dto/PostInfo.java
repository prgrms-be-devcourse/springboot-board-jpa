package com.programmers.kwonjoosung.board.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.programmers.kwonjoosung.board.model.Post;

import java.time.LocalDateTime;

public record PostInfo(

        String title,

        String content,

        String userName,
        @JsonFormat(shape = Shape.STRING, pattern = "yyyy-mm-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {
    public static PostInfo of(Post post) {
        return new PostInfo(post.getTitle(),
                post.getContent(),
                post.getUser().getName(),
                post.getCreatedAt());
    }
}
