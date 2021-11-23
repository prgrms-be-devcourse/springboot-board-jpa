package com.example.springbootboard.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {

    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String createdBy;

    @Builder
    public PostDto(Long postId, String title, String content, LocalDateTime createdAt, String createdBy) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}
