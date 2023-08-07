package com.prgrms.board.post.controller.dto;

import java.time.LocalDateTime;

public record PostDetailedResponse(
        Long id,
        String title,
        String content,
        String createdBy,
        LocalDateTime createdDate
) {
}
