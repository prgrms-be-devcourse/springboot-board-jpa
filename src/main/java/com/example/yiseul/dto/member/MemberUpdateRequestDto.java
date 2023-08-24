package com.example.yiseul.dto.member;

import jakarta.validation.constraints.Min;

public record MemberUpdateRequestDto(
    @Min(value = 1)
    String name,

    Integer age,

    @Min(value = 1)
    String hobby
) {
}
