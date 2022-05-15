package com.study.board.fixture;

import com.study.board.domain.user.domain.User;

import java.util.Optional;

public class Fixture {

    public static User createUser() {
        return User.create("득윤", "체스");
    }

}
