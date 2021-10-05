package com.devcourse.bbs.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {
    private final long id;
    private final String name;
    private final int age;
    private final String hobby;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
        this.hobby = user.getHobby();
    }
}
