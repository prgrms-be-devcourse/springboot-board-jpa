package com.example.board.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCreateRequest(@NotNull Long userId, @NotBlank @Size(max = TITLE_MAX) String title,
                                @NotBlank @Size(max = CONTENT_MAX) String content) {

  private static final int TITLE_MAX = 30;
  private static final int CONTENT_MAX = 100;
}