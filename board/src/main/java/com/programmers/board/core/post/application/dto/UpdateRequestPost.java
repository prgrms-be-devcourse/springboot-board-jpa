package com.programmers.board.core.post.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateRequestPost {

    private Long id;

    private String title;

    private String content;

    @Builder
    public UpdateRequestPost(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
