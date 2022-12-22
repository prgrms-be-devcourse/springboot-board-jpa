package com.example.board.global.validator;

import com.example.board.domain.user.entity.User;
import org.springframework.util.Assert;

import java.util.Optional;

public class UserValidator {

    public static User validateOptionalUserExists(Optional<User> mayBeUser) {
        if (mayBeUser.isEmpty()) {
            throw new IllegalArgumentException("exception.entity.user");
        }
        return mayBeUser.get();
    }

    public static void validateName(String name) {
        Assert.hasText(name, "{exception.entity.user.name.length}");
    }

    public static void validateAge(int age) {
        if (0 >= age) {
            throw new IllegalArgumentException("{exception.entity.user.age.length}");
        }
    }
}
