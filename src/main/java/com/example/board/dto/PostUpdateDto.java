package com.example.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostUpdateDto(
        @NotNull(message = "사용자 id를 입력해주세요.")
        Long userId,
        @NotBlank(message = "게시글 제목을 입력해주세요.")
        @Size(max = 20, message = "제목은 최대 20자까지만 입력해주세요.")
        String title,
        @NotBlank(message = "게시글 내용을 입력해주세요.")
        String contents
) {
}
