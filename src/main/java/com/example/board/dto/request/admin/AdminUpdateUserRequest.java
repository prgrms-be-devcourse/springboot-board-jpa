package com.example.board.dto.request.admin;

import com.example.board.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record AdminUpdateUserRequest(

        @Email(message = "이메일은 필수 입력값입니다.")
        String email,

        @Size(min = 2, max = 10, message = "이름은 2 ~ 10자 사이로 입력해 주세요.")
        String name,

        @Min(value = 1, message = "나이는 1살 이상으로 입력해 주세요.")
        @Max(value = 100, message = "나이는 100살 이하로 입력해 주세요.")
        Integer age,

        @Size(min = 2, max = 20, message = "취미는 2 ~ 20자 사이로 입력해 주세요.")
        String hobby,
        
        Role role
) {
}
