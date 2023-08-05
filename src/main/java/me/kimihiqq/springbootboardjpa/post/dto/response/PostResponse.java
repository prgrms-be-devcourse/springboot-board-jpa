package me.kimihiqq.springbootboardjpa.post.dto.response;

import lombok.Getter;
import me.kimihiqq.springbootboardjpa.post.domain.Post;

import java.time.LocalDateTime;

@Getter
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final Long userId;
    private final LocalDateTime createdAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userId = post.getUser().getId();
        this.createdAt = post.getCreatedAt();
    }
}
