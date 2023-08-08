package me.kimihiqq.springbootboardjpa.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimihiqq.springbootboardjpa.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UserCreateRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private int age;

    @NotEmpty
    private String hobby;

    public User toEntity() {
        return User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }
}
