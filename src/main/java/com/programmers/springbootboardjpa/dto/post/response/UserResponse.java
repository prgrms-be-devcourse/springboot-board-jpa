package com.programmers.springbootboardjpa.dto.post.response;

import java.time.LocalDateTime;

public class UserResponse {

    private Long userId;

    private String name;

    private Long age;

    private String hobby;

    private String createdBy;

    private LocalDateTime createdAt;

    public UserResponse(Long userId, String name, Long age, String hobby, String createdBy, LocalDateTime createdAt) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public static class UserResponseBuilder {

        private Long userId;

        private String name;

        private Long age;

        private String hobby;

        private String createdBy;

        private LocalDateTime createdAt;

        public UserResponseBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public UserResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserResponseBuilder age(Long age) {
            this.age = age;
            return this;
        }

        public UserResponseBuilder hobby(String hobby) {
            this.hobby = hobby;
            return this;
        }

        public UserResponseBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public UserResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(this.userId, this.name, this.age, this.hobby, this.createdBy, this.createdAt);
        }

    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Long getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
