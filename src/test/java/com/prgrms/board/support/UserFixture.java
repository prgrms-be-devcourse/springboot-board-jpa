package com.prgrms.board.support;

import com.prgrms.board.domain.user.entity.User;

public class UserFixture {

    public static User create() {
        return User.create("test", 10, "post");
    }
}
