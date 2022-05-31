package com.dojinyou.devcourse.boardjpa.user.service.dto;

public class UserCreateDto {
    private final String name;
    private final int age;
    private final String hobby;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    private UserCreateDto(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.hobby = builder.hobby;
    }

    public static class Builder {
        private String name;
        private int age;
        private String hobby;

        public Builder() {
        }

        public Builder name(String name) {
            this.name = name;
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

        public UserCreateDto build() {
            return new UserCreateDto(this);
        }
    }
}
