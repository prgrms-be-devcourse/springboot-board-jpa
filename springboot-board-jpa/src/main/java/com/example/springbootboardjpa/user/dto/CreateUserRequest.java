package com.example.springbootboardjpa.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUserRequest {
    private int age;
    private String name;
    private String hobby;

    @Builder
    public CreateUserRequest(int age, String name, String hobby) {
        this.age = age;
        this.name = name;
        this.hobby = hobby;
    }
}
