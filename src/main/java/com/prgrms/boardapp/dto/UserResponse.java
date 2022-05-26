package com.prgrms.boardapp.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class UserResponse {
    private Long id;
    private String name;
    private Integer age;
    private String hobby;

    private UserResponse(Long id, String name, Integer age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("age", age)
                .append("hobby", hobby)
                .toString();
    }

    public static UserResponseBuilder builder() {
        return new UserResponseBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse userDto = (UserResponse) o;
        return Objects.equals(id, userDto.id);
    }

    public static class UserResponseBuilder {
        private Long id;
        private String name;
        private Integer age;
        private String hobby;

        UserResponseBuilder() {
        }

        public UserResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public UserResponseBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public UserResponseBuilder age(final Integer age) {
            this.age = age;
            return this;
        }

        public UserResponseBuilder hobby(final String hobby) {
            this.hobby = hobby;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(this.id, this.name, this.age, this.hobby);
        }
    }
}
