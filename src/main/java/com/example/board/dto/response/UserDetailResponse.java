package com.example.board.dto.response;

import com.example.board.domain.Role;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDetailResponse(
        Long id,
        String email,
        String name,
        Integer age,
        String hobby,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt

) {
}
