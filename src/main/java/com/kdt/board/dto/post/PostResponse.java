package com.kdt.board.dto.post;

import com.kdt.board.domain.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {
    private Long postId;
    private String title;
    private String userName;
    private LocalDateTime createdAt;

    // note : converter, 생성자, factory method pattern
    public PostResponse(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.userName = post.getUserName();
        this.createdAt = post.getCreatedAt();
    }
}
