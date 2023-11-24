package com.example.board.dto.request.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

        @NotBlank(message = "이름을 입력해 주세요.")
        @Size(min = 2, message = "이름은 최소 2글자 이상 입력해주세요.")
        String name,

        @NotNull(message = "나이를 입력해주세요.")
        @Min(value = 1, message = "나이는 0살 이상 입력해주세요.")
        Integer age,

        String hobby
) {
}
