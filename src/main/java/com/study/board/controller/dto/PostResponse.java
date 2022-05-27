package com.study.board.controller.dto;

import com.study.board.domain.post.domain.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class PostResponse {

    private final long postId;
    private final String title;
    private final String content;
    private final String writer;
    private final LocalDateTime writtenDateTime;

    public static PostResponse convert(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getWriter().getName(),
                post.getWrittenDateTime()
        );
    }
}
