package com.example.board.domain.post.validator;

import org.springframework.util.Assert;

public class PostValidator {

    public void validateTitle(String title) {
        Assert.hasText(title, "{exception.entity.post.title.length}");
    }

    public void validateContent(String content) {
        Assert.hasText(content, "{exception.entity.post.content.length}");
    }
}
