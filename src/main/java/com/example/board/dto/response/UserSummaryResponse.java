package com.example.board.dto.response;

import lombok.Builder;

@Builder
public record UserSummaryResponse(
        Long id,
        String email,
        String name,
        Integer age
) {
}
