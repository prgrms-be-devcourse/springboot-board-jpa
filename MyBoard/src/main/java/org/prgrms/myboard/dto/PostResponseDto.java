package org.prgrms.myboard.dto;

import java.time.LocalDateTime;

public record PostResponseDto(
    Long id,
    String title,
    String content,
    String createdBy,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
