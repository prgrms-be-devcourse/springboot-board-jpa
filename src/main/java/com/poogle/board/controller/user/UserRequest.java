package com.poogle.board.controller.user;

import com.poogle.board.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@AllArgsConstructor
public class UserRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private int age;

    @NotEmpty
    private String hobby;

    protected UserRequest() {
    }

    public User newUser() {
        return User.of(name, age, hobby);
    }
}
