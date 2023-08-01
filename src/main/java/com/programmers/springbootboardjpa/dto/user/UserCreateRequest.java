package com.programmers.springbootboardjpa.dto.user;

import com.programmers.springbootboardjpa.domain.user.Hobby;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreateRequest {

    private final String name;
    private final Integer age;
    private final Hobby hobby;

    @Builder
    public UserCreateRequest(String name, Integer age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

}
