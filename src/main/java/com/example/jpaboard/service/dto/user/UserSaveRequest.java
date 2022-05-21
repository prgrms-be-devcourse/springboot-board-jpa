package com.example.jpaboard.service.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class UserSaveRequest {
    @NotBlank(message = "이름은 필수 값입니다")
    private final String name;
    @PositiveOrZero
    private final int age;
    private final String hobby;

    public UserSaveRequest(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }
}
