package com.prgrms.springboard.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import com.prgrms.springboard.user.domain.User;

import lombok.Getter;

@Getter
public class CreateUserRequest {

    @NotBlank(message = "이름은 비거나 공백만 있을 수 없습니다.")
    @Length(max = 10, message = "이름은 {max}글자 이하여야합니다.")
    private final String name;

    @NotNull(message = "나이는 비어있을 수 없습니다.")
    @PositiveOrZero(message = "나이는 양수 또는 0이여야 합니다.")
    private final Integer age;

    @NotBlank(message = "취미는 비거나 공백만 있을 수 없습니다.")
    @Length(max = 15, message = "취미는 {max}글자 이하여야합니다.")
    private final String hobby;

    public CreateUserRequest(String name, Integer age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public User toEntity() {
        return User.of(name, age, hobby);
    }
}
