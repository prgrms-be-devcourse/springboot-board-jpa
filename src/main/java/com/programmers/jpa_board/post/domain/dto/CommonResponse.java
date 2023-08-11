package com.programmers.jpa_board.post.domain.dto;

import java.time.LocalDateTime;

public record CommonResponse(Long id, String title, String content, Long userId, String createdBy, LocalDateTime createAt) implements PostDto {
}
