package com.example.board.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInRequest(

        @NotBlank(message = "이름을 입력해 주세요.")
        @Size(min = 2, max = 20, message = "이름은 최소 2글자 이상, 최대 20글자 이하로 입력해주세요.")
        String name,

        @NotBlank(message = "비밀번호를 입력해 주세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 최소 8글자 이상, 최대 20글자 이하로 입력해주세요.")
        String password
) {
}
