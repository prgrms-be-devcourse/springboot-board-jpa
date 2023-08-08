package com.blackdog.springbootBoardJpa.domain.post.service.dto;

import org.springframework.data.domain.Page;

public record PostResponses(
        Page<PostResponse> postResponses
) {
}
