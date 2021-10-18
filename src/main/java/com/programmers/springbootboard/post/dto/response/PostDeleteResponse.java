package com.programmers.springbootboard.post.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDeleteResponse {
    private Long id;
    private String email;
}
