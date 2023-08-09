package com.jpaboard.user.ui.dto;


public record Request(
        String name, int age, String hobby
) implements UserDto{
}
