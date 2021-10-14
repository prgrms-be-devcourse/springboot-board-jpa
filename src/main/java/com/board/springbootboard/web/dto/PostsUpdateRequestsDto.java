package com.board.springbootboard.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestsDto {

    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestsDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
