package com.example.yiseul.dto.member;

import jakarta.validation.constraints.Size;

public record MemberUpdateRequestDto(
    @Size(min = 1, message = "취미는 1글자 이상 입력해야 합니다.")
    String name,

    Integer age,

    @Size(min = 1, message = "취미는 1글자 이상 입력해야 합니다.")
    String hobby
) {
}
