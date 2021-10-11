package org.prgms.board.user.dto;

import lombok.Getter;
import org.prgms.board.domain.entity.User;

import javax.validation.constraints.NotNull;

@Getter
public class UserRequest {
    @NotNull(message = "이름을 입력해주세요")
    private String name;
    private int age;
    private String hobby;

    public UserRequest() {
    }

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
