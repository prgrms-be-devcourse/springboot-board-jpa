package com.prgrms.board.post.service.dto;

import java.time.LocalDateTime;

public record PostDetailedResult(
        Long id,
        String title,
        String content,
        String createdBy,
        LocalDateTime createdDate
) {
}
