package com.example.yiseul.dto.member;

import jakarta.validation.constraints.Pattern;

public record MemberUpdateRequestDto(
        @Pattern(regexp = "\\S+", message = "최소 1글자 이상 입력해야 합니다.")
        String name,

        Integer age,

        @Pattern(regexp = "\\S+", message = "최소 1글자 이상 입력해야 합니다.")
        String hobby) {
}
