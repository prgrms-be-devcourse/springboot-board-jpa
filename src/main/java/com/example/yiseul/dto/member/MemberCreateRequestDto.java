package com.example.yiseul.dto.member;

import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequestDto(
    @NotBlank
    String name,

    Integer age,

    @NotBlank
    String hobby

) {
}