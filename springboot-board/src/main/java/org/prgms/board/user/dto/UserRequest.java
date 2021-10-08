package org.prgms.board.user.dto;

import lombok.Getter;
import org.prgms.board.domain.entity.User;

@Getter
public class UserRequest {
    private String name;
    private int age;
    private String hobby;

    public UserRequest() {
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }
}
