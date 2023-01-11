package com.example.board.domain.user.validator;

import org.springframework.util.Assert;

public class UserValidator {

    public void validateName(String name) {
        Assert.hasText(name, "{exception.entity.user.name.length}");
    }

    public void validateAge(int age) {
        if (0 >= age) {
            throw new IllegalArgumentException("{exception.entity.user.age.length}");
        }
    }
}
