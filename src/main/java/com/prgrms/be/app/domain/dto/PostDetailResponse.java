package com.prgrms.be.app.domain.dto;

import java.time.LocalDateTime;

public record PostDetailResponse(
        String title,
        String content,
        Long postId,
        LocalDateTime createdAt,
        Long userId,
        String userName) {
}
