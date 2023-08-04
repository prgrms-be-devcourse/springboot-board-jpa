package com.programmers.jpa_board.post.domain.dto.response;

import java.time.LocalDateTime;

public record PostResponse(Long id, String title, String content, Long userId, String createdBy, LocalDateTime createAt) {
}
