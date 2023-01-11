package com.example.springbootboardjpa.domain.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveResponseDto {

    private long postId;
    private String title;
    private String content;
    private String createdAt;
    private long createdBy;
}
