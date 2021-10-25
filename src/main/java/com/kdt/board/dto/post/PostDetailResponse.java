package com.kdt.board.dto.post;

import com.kdt.board.domain.Post;
import com.kdt.board.dto.comment.CommentResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDetailResponse {
    private Long postId;
    private String title;
    private String content;
    private List<CommentResponse> comments;
    private String userName;
    private LocalDateTime createdAt;

    public PostDetailResponse(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.userName = post.getUserName();
        this.comments = post.getComments().stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }
}
