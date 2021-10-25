package com.kdt.board.dto.user;

import com.kdt.board.dto.comment.CommentResponse;
import com.kdt.board.dto.post.PostResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserResponse {
    private Long userId;
    private List<PostResponse> posts;
    private List<CommentResponse> comments;
    private LocalDateTime createdAt;

    public UserResponse(Long userId, List<PostResponse> posts, List<CommentResponse> comments, LocalDateTime createdAt) {
        this.userId = userId;
        this.posts = posts;
        this.comments = comments;
        this.createdAt = createdAt;
    }
}
