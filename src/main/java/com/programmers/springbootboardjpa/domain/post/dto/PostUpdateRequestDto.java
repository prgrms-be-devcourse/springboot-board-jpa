package com.programmers.springbootboardjpa.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PostUpdateRequestDto(
        @NotBlank(message = "제목을 입력해주세요.")
        @Size(max = 50, message = "제목 길이는 50자 이내여야 합니다.")
        String title,

        @NotBlank(message = "내용을 입력해주세요.")
        String content
) {
}
