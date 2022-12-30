package com.prgrms.dto;

import com.prgrms.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDto {

    public record Request(@NotBlank String name, @NotBlank String hobby, @NotNull int age) {

        public User toUser() {
            return new User(name, hobby, age);
        }
    }

    public record Response(User user) {

        public User toUser() {
            return new User(user.getId(), user.getName(), user.getHobby(), user.getAge());
        }

        public long getUserId() {
            return user().getId();
        }
    }

}
