package com.jpaboard.domain.user.dto.response;

import lombok.Builder;

@Builder
public record UserResponse (
        Long id,
        String name,
        int age,
        String hobby
) {
}
