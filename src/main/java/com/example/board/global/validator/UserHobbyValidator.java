package com.example.board.global.validator;

import com.example.board.domain.hobby.entity.Hobby;
import com.example.board.domain.user.entity.User;
import org.springframework.util.Assert;

public class UserHobbyValidator {

    public static void validateUserNull(User user) {
        Assert.notNull(user, "{exception.entity.user.null}");
    }

    public static void validateHobbyNull(Hobby hobby) {
        Assert.notNull(hobby, "{exception.entity.hobby.null}");
    }
}
