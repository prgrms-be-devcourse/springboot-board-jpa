package com.prgrms.jpa.controller.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUserRequest {

    @NotBlank(message = "이름은 필수 입력사항입니다.")
    @Size(max = 30, message = "이름은 {max}글자 이하로 입력할 수 있습니다.")
    private String name;

    @NotNull(message = "나이는 필수 입력사항입니다.")
    @Positive(message = "나이는 0 또는 음수일 수 없습니다.")
    private int age;

    @NotBlank(message = "취미는 필수 입력사항입니다.")
    @Size(max = 30, message = "취미는 {max}글자 이하로 입력할 수 있습니다.")
    private String hobby;

    @Builder
    public CreateUserRequest(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
