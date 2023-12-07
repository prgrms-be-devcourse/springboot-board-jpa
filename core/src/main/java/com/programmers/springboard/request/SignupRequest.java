package com.programmers.springboard.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupRequest (
        @NotBlank(message = "아이디를 입력해주세요") String loginId,
        @NotBlank (message = "비밀번호를 입력해주세요") String passwd,
        @NotBlank (message = "이름은 공백이 될 수 없습니다") String name,
        Integer age,
        String hobby,
        @NotNull Long groupId){}
