package com.example.board.domain.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(@NotBlank @Size(max = NAME_MAX) String name, @NotNull
@Min(AGE_MIN) Integer age, @NotBlank @Size(max = HOBBY_MAX) String hobby) {

  private static final int NAME_MAX = 30;
  private static final int AGE_MIN = 1;
  private static final int HOBBY_MAX = 100;
}