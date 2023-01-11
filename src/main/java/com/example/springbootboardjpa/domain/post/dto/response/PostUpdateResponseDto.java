package com.example.springbootboardjpa.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PostUpdateResponseDto {

    private long postId;
    private String title;
    private String content;
    private long createdBy;
    private String createdAt;
}
