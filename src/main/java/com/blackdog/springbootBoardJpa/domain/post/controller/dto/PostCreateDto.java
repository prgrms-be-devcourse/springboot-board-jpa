package com.blackdog.springbootBoardJpa.domain.post.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCreateDto(
        @NotBlank(message = "title은 공백일 수 없습니다.")
        String title,

        @NotNull
        @Size(max = 255, message = "내용은 최대 255자 입니다.")
        String content
) {
}
