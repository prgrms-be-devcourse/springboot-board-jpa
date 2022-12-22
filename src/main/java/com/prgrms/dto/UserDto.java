package com.prgrms.dto;

import com.prgrms.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDto {

    public record Request(@NotBlank String name, @NotBlank String hobby, @NotNull int age) {

        public User toUser() {
            return new  User(name, hobby, age);
        }
    }

    public record Response(@NotNull Long id, @NotBlank String name, @NotBlank String hobby, @NotNull int age) {

        public User toUser() {
            return new User(id, name, hobby, age);
        }
    }

}
