package com.poogle.board.controller.user;

import com.poogle.board.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private @NotEmpty String name;
    private @NotEmpty int age;
    private @NotEmpty String hobby;

    public User newUser() {
        return User.of(name, age, hobby);
    }
}
