package com.example.springbootboardjpa.dto.user.request;

import com.example.springbootboardjpa.entity.User;
import com.example.springbootboardjpa.enums.Hobby;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequest {
    private String name;
    private int age;
    private Hobby hobby;

    public User toEntity() {
        return User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }
}
