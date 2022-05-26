package com.example.springbootboardjpa.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private PostUserDto postUserDto;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public PostDto(Long id, String title, String content, PostUserDto postUserDto, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postUserDto = postUserDto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
