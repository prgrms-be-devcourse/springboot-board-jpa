package com.prgrms.work.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {

    private static final String NAME = "김형욱";
    private static final int AGE = 27;
    private static final String HOBBY = "산책";

    @Test
    void 객체_생성() {
       User user = User.create("김형욱", 27, "산책");

        assertThat(user.getName()).isEqualTo(NAME);
        assertThat(user.getAge()).isEqualTo(AGE);
        assertThat(user.getHobby()).isEqualTo(HOBBY);
    }

    @Test
    void 객체_생성_실패() {

    }

}