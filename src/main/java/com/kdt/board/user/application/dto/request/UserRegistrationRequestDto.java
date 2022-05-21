package com.kdt.board.user.application.dto.request;

import com.kdt.board.user.domain.User;

public class UserRegistrationRequestDto {

    private final String name;
    private final String email;
    private final int age;
    private final String hobby;

    private UserRegistrationRequestDto(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.age = builder.age;
        this.hobby = builder.hobby;
    }

    public User toEntity() {
        return new User.Builder()
                .name(name)
                .email(email)
                .age(age)
                .hobby(hobby)
                .build();
    }

    public static class Builder {
        private String name;
        private String email;
        private int age;
        private String hobby;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder hobby(String hobby) {
            this.hobby = hobby;
            return this;
        }

        public UserRegistrationRequestDto build() {
            return new UserRegistrationRequestDto(this);
        }
    }
}
