package org.prgms.board.user.dto;

import lombok.Getter;
import org.prgms.board.domain.entity.User;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class UserRequest {
    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @Positive
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
