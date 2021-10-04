package com.programmers.springbootboard.post.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDetailResponse {
    private String title;
    private String content;
    private String email;
}
