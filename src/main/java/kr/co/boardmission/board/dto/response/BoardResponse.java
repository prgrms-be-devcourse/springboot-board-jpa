package kr.co.boardmission.board.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BoardResponse(
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        String createdBy,
        String modifiedBy
) {
}