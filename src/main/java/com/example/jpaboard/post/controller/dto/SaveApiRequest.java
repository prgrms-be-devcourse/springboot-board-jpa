package com.example.jpaboard.post.controller.dto;

import jakarta.validation.constraints.NotNull;

public record SaveApiRequest(@NotNull Long memberId, @NotNull String title, @NotNull String content) {
}
