package com.prgrms.boardapp.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.prgrms.boardapp.common.UserCreateUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @Test
    @DisplayName("User 생성 성공 테스트")
    void testSuccess() {
        User user = createUserWithId();

        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user.getId()).isNotNull()
        );
    }

    @Test
    @DisplayName("이름 필드의 MAX를 넘어갈 수 없다.")
    void testNameValidateLength() {
        String maxOver = "*".repeat(User.NAME_MAX_LENGTH + 1);

        assertThrows(IllegalArgumentException.class, () -> createUserWithName(maxOver));
    }

    @Test
    @DisplayName("이름 필드는 null이나 공백을 허용하지 않는다.")
    void testNameValidateNotNull() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> createUserWithName(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> createUserWithName("")),
                () -> assertThrows(IllegalArgumentException.class, () -> createUserWithName("     "))
        );
    }

    @Test
    @DisplayName("나이는 음수를 허용하지 않는다.")
    void testAgeMinus() {
        assertThrows(IllegalArgumentException.class, () -> createUserWithAge(-1));
    }

}