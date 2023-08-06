package com.jpaboard.user.ui.dto;

import lombok.Builder;

@Builder
public record UserResponse(
        String name, int age, String hobby
) {
}
