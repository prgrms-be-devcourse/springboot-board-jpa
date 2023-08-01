package kr.co.boardmission.board.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BoardSummary(
        String title,
        LocalDateTime createdAt,
        String createdBy
) {
}
