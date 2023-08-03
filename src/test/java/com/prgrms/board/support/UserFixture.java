package com.prgrms.board.support;

import com.prgrms.board.domain.user.entity.User;

public class UserFixture {

    public static User create() {
        return User.create("이름", 10, "취미");
    }

    public static User createByBuilder() {
        return User.builder()
            .id(1L)
            .name("이름")
            .age(10)
            .hobby("취미")
            .build();
    }
}
