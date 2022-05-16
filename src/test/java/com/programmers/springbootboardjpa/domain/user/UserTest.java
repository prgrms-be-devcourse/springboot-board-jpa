package com.programmers.springbootboardjpa.domain.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class UserTest {
    @Test
    void 유저를_생성합니다(){
        User user = User.create("User1");

        assertThat(user.getId()).isNull();
        assertThat(user.getCreatedAt()).isNull();
        assertThat(user.getName()).isNotNull();
    }

    @Test
    void 유저_name이_null일경우(){
        assertThatIllegalArgumentException().isThrownBy(()
                -> User.create(null)
        );
    }

}