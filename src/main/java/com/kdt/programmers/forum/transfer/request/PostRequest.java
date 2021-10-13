package com.kdt.programmers.forum.transfer.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequest {

    private String title;
    private String content;

    public PostRequest(String title, String content) {
        validateTitle(title);
        this.title = title;
        this.content = content;
    }

    private void validateTitle(String title) {
        final int TITLE_LENGTH_LIMIT = 60;
        if (title.length() > TITLE_LENGTH_LIMIT)
            throw new IllegalArgumentException("length of title must be less than " + TITLE_LENGTH_LIMIT);
    }
}
