package com.kdt.board.user.application.dto.request;

public class UserRegistrationRequestDto {

    private String name;
    private String email;
    private int age;
    private String hobby;

    private UserRegistrationRequestDto(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.age = builder.age;
        this.hobby = builder.hobby;
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
