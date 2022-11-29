package com.springboard.user.dto;

import java.time.LocalDateTime;

public record UserResponse(
    Long id,
    String name,
    Integer age,
    String hobby,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}