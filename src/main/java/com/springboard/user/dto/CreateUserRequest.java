package com.springboard.user.dto;

public record CreateUserRequest(
    String name,
    Integer age,
    String hobby
) {}