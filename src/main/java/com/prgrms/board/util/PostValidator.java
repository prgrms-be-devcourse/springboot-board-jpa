package com.prgrms.board.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostValidator {
    public static void validatePostTitle(String title) {
        Assert.hasText(title, "{exception.post.title.null}");
    }

    public static void validatePostContent(String content) {
        Assert.hasText(content, "{exception.post.content.null}");
    }
}
