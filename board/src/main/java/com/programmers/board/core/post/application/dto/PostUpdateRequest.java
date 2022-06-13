package com.programmers.board.core.post.application.dto;

import com.programmers.board.core.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateRequestPost {

    private String title;

    private String content;

    @Builder
    public UpdateRequestPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
