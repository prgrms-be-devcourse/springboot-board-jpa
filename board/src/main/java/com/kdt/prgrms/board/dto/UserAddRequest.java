package com.kdt.prgrms.board.dto;

import com.kdt.prgrms.board.entity.user.Hobby;
import com.kdt.prgrms.board.entity.user.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class UserAddRequest {

    @NotBlank
    private final String name;

    @Min(0)
    private final int age;

    private final Hobby hobby;

    public UserAddRequest(String name, int age, Hobby hobby) {

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

    public Hobby getHobby() {

        return hobby;
    }

    public User toEntity() {

        return User.builder()
                .name(this.name)
                .age(this.age)
                .hobby(this.hobby)
                .build();
    }
}
