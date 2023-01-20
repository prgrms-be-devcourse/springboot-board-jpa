package com.example.springbootboardjpa.domain.post.dto.response;

import com.example.springbootboardjpa.domain.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostFindByIdResponseDto {

    private long postId;
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    protected PostFindByIdResponseDto() {

    }

    public PostFindByIdResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }
}
