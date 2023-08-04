package dev.jpaboard.user.dto.request;

import dev.jpaboard.user.domain.User;

public record UserCreateRequest(String email, String password, String name, int age, String hobby) {

    public User toUser() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .age(this.age)
                .hobby(this.hobby)
                .build();
    }

}
