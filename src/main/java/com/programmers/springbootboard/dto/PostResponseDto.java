package com.programmers.springbootboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.programmers.springbootboard.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponseDto {

    @JsonProperty(value = "post_id")
    private Long id;

    private String title;

    private String content;

    @JsonProperty(value = "created_by")
    private String userName;

    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;

    public PostResponseDto(Long id, String title, String content, String name, LocalDateTime created_at) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userName = name;
        this.createdAt = created_at;
    }

    public static PostResponseDto of(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getName(),
                post.getCreatedAt()
        );
    }
}
