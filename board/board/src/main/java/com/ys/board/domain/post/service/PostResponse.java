package com.ys.board.domain.post.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ys.board.domain.post.Post;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

    private Long postId;

    private String title;

    private String content;

    private Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm-ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private Long createdBy;

    public PostResponse(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userId = post.getUser().getId();
        this.createdAt = post.getCreatedAt();
        this.createdBy = post.getCreatedBy();
    }

}
