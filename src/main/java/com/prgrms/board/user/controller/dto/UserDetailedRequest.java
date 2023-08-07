package com.prgrms.board.user.controller.dto;

import jakarta.validation.constraints.*;

public record UserDetailedRequest(
        @NotBlank
        @Size(min = 2, max = 20)
        String name,

        @NotNull
        @Min(value = 0)
        @Max(value = 120)
        Integer age,

        String hobby
) {
}