package com.kdt.Board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private UserResponse userResponse;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public PostResponse(Long id, String title, String content, UserResponse userResponse, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userResponse = userResponse;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
