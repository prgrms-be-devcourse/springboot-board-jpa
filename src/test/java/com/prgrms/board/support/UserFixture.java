package com.prgrms.board.support;

import com.prgrms.board.domain.user.entity.User;

public class UserFixture {

    private Long id;
    private String name = "이름";
    private int age = 10;

    private UserFixture() {
    }

    public static UserFixture user() {
        return new UserFixture();
    }

    public UserFixture id(Long id) {
        this.id = id;
        return this;
    }

    public User build() {
        return User.builder()
            .id(id)
            .name(name)
            .age(age)
            .build();
    }
}
