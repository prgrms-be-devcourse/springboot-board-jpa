package com.programmers.board.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class UserUpdateRequest {
    @Pattern(regexp = "^(?=.*[A-Za-z])[A-Za-z\\d]{1,30}$",
            message = "사용자 이름은 1자 이상, 30자 이하의 영문자 또는 영문자와 숫자로 구성되어야 합니다.")
    private final String name;

    @PositiveOrZero(message = "사용자 나이는 0 이상이어야 합니다")
    private final int age;

    @Pattern(regexp = "^[A-Za-z가-힣]{1,50}$",
            message = "사용자 취미는 1자 이상, 50자 이하의 영문자여야 합니다")
    private final String hobby;

    public UserUpdateRequest(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
