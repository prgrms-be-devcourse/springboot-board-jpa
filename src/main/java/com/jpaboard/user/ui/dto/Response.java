package com.jpaboard.user.ui.dto;


public record Response(
        String name, int age, String hobby
) implements UserDto {
}
