package com.springboard.user.dto;

public record UpdateUserRequest(
    Integer age,
    String hobby
) {}