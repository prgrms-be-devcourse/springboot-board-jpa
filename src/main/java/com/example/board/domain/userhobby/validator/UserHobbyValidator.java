package com.example.board.domain.userhobby.validator;

import org.springframework.util.Assert;

import com.example.board.domain.hobby.entity.Hobby;
import com.example.board.domain.user.entity.User;

public class UserHobbyValidator {

    public void validateUserNull(User user) {
        Assert.notNull(user, "{exception.entity.user.null}");
    }

    public void validateHobbyNull(Hobby hobby) {
        Assert.notNull(hobby, "{exception.entity.hobby.null}");
    }
}
