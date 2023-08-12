package com.programmers.springbootboardjpa.domain.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserRequestDto(
        @NotBlank(message = "이름을 입력해주세요.")
        @Size(max = 20, message = "이름 길이는 20자 이내여야 합니다.")
        String name,

        @Min(value = 0, message = "나이는 0세 이상이어야 합니다.")
        int age,

        @NotBlank(message = "취미를 입력해주세요.")
        @Size(max = 30, message = "취미 길이는 30자 이내여야 합니다.")
        String hobby
) {
}
