package com.programmers.boardjpa.user.entity;

import com.programmers.boardjpa.user.exception.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class UserTest {

    @Test
    @DisplayName("User 생성 시 잘못된 나이 범위는 예외를 발생시킨다.")
    void testInvalidAgeRangeException() {
        // given
        int age = 120;
        String name = "김뫄뫄";
        String hobby = "SLEEP";

        // when - then
        assertThatThrownBy(() -> new User(name, age, hobby))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("올바르지 않은 나이 범위입니다.");
    }
}
