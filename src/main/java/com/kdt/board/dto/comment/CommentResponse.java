package com.kdt.board.dto.comment;

import com.kdt.board.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private final Long commentId;
    private final String userName;
    private final String content;
    private final LocalDateTime createdAt;

    public CommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.userName = comment.getUserName();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
