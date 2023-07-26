package com.prgrms.boardjpa.User.dto;

import com.prgrms.boardjpa.User.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public final class UserRequest {
    private String name;
    private int age;
    private String hobby;

    @Builder
    public UserRequest(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }
}