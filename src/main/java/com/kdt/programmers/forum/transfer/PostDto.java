package com.kdt.programmers.forum.transfer;

import lombok.Getter;

@Getter
public class PostDto {
    private final int TITLE_LENGTH_LIMIT = 60;

    private Long id;
    private final String title;
    private final String content;

    public PostDto(String title, String content) {
        validateTitle(title);
        this.title = title;
        this.content = content;
    }

    public PostDto(Long id, String title, String content) {
        validateId(id);
        this.id = id;

        validateTitle(title);
        this.title = title;
        this.content = content;
    }

    private void validateId(Long id) {
        if (id <= 0)
            throw new IllegalArgumentException("id must be larger than 0");
    }

    private void validateTitle(String title) {
        if (title.length() > TITLE_LENGTH_LIMIT)
            throw new IllegalArgumentException("length of title must be less than " + TITLE_LENGTH_LIMIT);
    }
}
