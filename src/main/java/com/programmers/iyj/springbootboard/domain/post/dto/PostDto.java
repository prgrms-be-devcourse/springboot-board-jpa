package com.programmers.iyj.springbootboard.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostDto {
    private Long id;
    private String title;
    private String content;
}
