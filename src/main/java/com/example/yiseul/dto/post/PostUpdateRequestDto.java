package com.example.yiseul.dto.post;

import jakarta.validation.constraints.Pattern;

public record PostUpdateRequestDto(
        @Pattern(regexp = "\\S+", message = "최소 1글자 이상 입력해야 합니다.")
        String title,

        @Pattern(regexp = "\\S+", message = "최소 1글자 이상 입력해야 합니다.")
        String content) {
}
