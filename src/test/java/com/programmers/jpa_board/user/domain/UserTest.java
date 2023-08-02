package com.programmers.jpa_board.user.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserTest {
    @Test
    void 이름이_한글_또는_영어가_아닐시_예외() {
        //when & then
        assertThatThrownBy(() -> User.builder()
                        .name("신범철!")
                        .age(26)
                        .hobby("헬스")
                        .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름이_공백인_경우_예외() {
        //when & then
        assertThatThrownBy(() -> User.builder()
                        .name("")
                        .age(26)
                        .hobby("헬스")
                        .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 나이가_100살_이상인_경우_예외() {
        //when & then
        assertThatThrownBy(() -> User.builder()
                        .name("신범철")
                        .age(200)
                        .hobby("헬스")
                        .build())
                .isInstanceOf(IllegalArgumentException.class);
    }
}
