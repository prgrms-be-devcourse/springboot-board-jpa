package com.programmers.springbootboardjpa.dto.user.request;

import com.programmers.springbootboardjpa.domain.user.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class UserCreationRequest {

    @NotBlank(message = "사용자 이름은 필수값입니다.")
    private String name;

    private Long age;

    private String hobby;

    public UserCreationRequest(String name, Long age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public String getName() {
        return name;
    }

    public Long getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    public User toEntity() {
        return new User(name,
                LocalDateTime.now(),
                name,
                age,
                hobby);
    }
}
