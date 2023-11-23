package com.example.board.dto.response;

import lombok.Builder;

@Builder
public record AuthorResponse(Long id, String name) {
}
