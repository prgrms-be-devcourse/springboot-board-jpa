package com.programmers.boardjpa.post.entity.vo;

import com.programmers.boardjpa.post.exception.PostErrorCode;
import com.programmers.boardjpa.post.exception.PostException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Title {
    private static final int MIN_TITLE_LEN = 0;
    private static final int MAX_TITLE_LEN = 255;

    private String title;

    public Title(String title) {
        validateTitle(title);
        this.title = title;
    }

    public Title changeTitle(String title) {
        validateTitle(title);
        this.title = title;

        return this;
    }

    private void validateTitle(String title) {
        int titleLen = title.length();

        if (titleLen <= MIN_TITLE_LEN || titleLen > MAX_TITLE_LEN) {
            throw new PostException(PostErrorCode.INVALID_TITLE_ERROR);
        }
    }
}
