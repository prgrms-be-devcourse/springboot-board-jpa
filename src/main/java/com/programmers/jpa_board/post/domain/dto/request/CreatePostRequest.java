package com.programmers.jpa_board.post.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
        @NotBlank
        @Size(max =  100, message = "최대 사이즈는 100자입니다.")
        String title,
        String content,
        @NotNull
        @Positive(message = "양수를 입력해주세요.")
        Long userId
) {
}
