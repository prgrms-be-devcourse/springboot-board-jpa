package com.example.springbootboardjpa.domain.post.dto.response;

import com.example.springbootboardjpa.domain.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostFindAllResponseDto {

    private long postId;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    protected PostFindAllResponseDto() {

    }

    public PostFindAllResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
    }
}
