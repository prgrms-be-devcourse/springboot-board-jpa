package com.example.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 추후 게시글 입력 항목 확장을 고려해 PostUpdateDto와 필드는 동일하지만 구분하여 사용할 예정입니다.
 */
public record PostDto(
        @NotNull(message = "사용자 id를 입력해주세요.")
        Long userId,
        @NotBlank(message = "게시글 제목을 입력해주세요.")
        @Size(max = 20, message = "제목은 최대 20자까지만 입력해주세요.")
        String title,
        @NotBlank(message = "게시글 내용을 입력해주세요.")
        String contents
) {
}
