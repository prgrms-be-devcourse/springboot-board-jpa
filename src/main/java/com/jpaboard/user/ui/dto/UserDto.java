package com.jpaboard.user.ui.dto;

import lombok.Builder;

@Builder
public record UserDto(
        String name, int age, String hobby
) {
}
