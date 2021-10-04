package com.programmers.springbootboard.post.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostUpdateRequest {
    private String email;
    private String title;
    private String content;
}
