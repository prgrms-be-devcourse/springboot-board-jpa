package com.study.board.fixture;

import com.study.board.domain.user.domain.User;

public class Fixture {

    public static User sampleUser() {
        return User.create("득윤", "체스");
    }


}
