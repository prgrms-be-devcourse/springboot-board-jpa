package com.programmers.jpa_board.user.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserDomainTest {
    @Test
    void 이름이_한글_또는_영어가_아닐시_예외() {
        //when & then
        assertThatThrownBy(() -> new User("신범철!", 26, "헬스"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름이_공백인_경우_예외() {
        //when & then
        assertThatThrownBy(() -> new User("", 26, "헬스"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 나이가_100살_이상인_경우_예외() {
        //when & then
        assertThatThrownBy(() -> new User("신범철", 200, "헬스"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
