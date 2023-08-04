package com.example.springbootboardjpa.dto.response;

import com.example.springbootboardjpa.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponse {

    private Long postId;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;
    private String createdBy;


    @Builder
    private PostResponse(Long postId, String title, String content, Long userId, LocalDateTime createdAt, String createdBy) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public static PostResponse fromEntity(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy())
                .build();
    }
}
