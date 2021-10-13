package com.kdt.programmers.forum.transfer;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PostDto {

    private final Long id;
    private final String title;
    private final String content;

    public PostDto(String title, String content) {
        this.id = null;
        this.title = title;
        this.content = content;
    }

    public PostDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
