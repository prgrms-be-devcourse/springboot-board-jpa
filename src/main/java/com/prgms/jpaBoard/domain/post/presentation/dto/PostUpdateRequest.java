package com.prgms.jpaBoard.domain.post.presentation.dto;

public record PostUpdateRequest(
        String title,
        String content
) {
}
