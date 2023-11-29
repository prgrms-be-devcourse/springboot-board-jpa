package com.example.board.dto.request.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @NotBlank(message = "이름을 입력해 주세요.")
        @Size(min = 2, max = 20, message = "이름은 최소 2글자 이상, 최대 20글자 이하로 입력해주세요.")
        String name,

        @NotNull(message = "나이를 입력해주세요.")
        @Max(value = 200, message = "나이는 200 이하로 입력해주세요.")
        Integer age,

        @Size(min = 1, max = 20, message = "취미는 최소 1글자 이상, 최대 20글자 이하로 입력해주세요.")
        String hobby
) {
}
