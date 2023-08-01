package com.programmers.jpaboard.dto.user.request;

import com.programmers.jpaboard.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequest {

    private String name;
    private Integer age;
    private String hobby;

    public User toEntity() {
        return User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }
}
