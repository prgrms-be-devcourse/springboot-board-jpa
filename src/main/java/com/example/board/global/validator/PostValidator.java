package com.example.board.global.validator;

import com.example.board.domain.post.entity.Post;
import org.springframework.util.Assert;

import java.util.Optional;

public class PostValidator {

    public static Post validateOptionalPostExists(Optional<Post> mayBePost) {
        if (mayBePost.isEmpty()) {
            throw new IllegalArgumentException("exception.entity.post");
        }
        return mayBePost.get();
    }

    public static void validateTitle(String title) {
        Assert.hasText(title, "{exception.entity.post.title.length}");
    }

    public static void validateContent(String content) {
        Assert.hasText(content, "{exception.entity.post.content.length}");
    }
}
