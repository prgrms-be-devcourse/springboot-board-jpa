package com.example.board.dto.request.user;

import jakarta.validation.constraints.*;

public record CreateUserRequest(

        @NotBlank(message = "이름은 필수 입력값입니다.")
        @Size(min = 2, max = 10, message = "이름은 2 ~ 10자 사이로 입력해 주세요.")
        String name,

        @NotNull(message = "나이는 필수 입력값입니다.")
        @Min(value = 1, message = "나이는 1살 이상으로 입력해 주세요.")
        @Max(value = 100, message = "나이는 100살 이하로 입력해 주세요.")
        Integer age,

        @Size(min = 2, max = 20, message = "취미는 2 ~ 20자 사이로 입력해 주세요.")
        String hobby
) {
}
