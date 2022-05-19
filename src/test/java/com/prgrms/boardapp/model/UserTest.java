package com.prgrms.boardapp.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.prgrms.boardapp.common.Utils.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {
    @Test
    @DisplayName("이름 필드의 MAX를 넘어갈 수 없다.")
    void testNameValidateLength() {
        assertThrows(IllegalArgumentException.class, () -> createUserWithName("*".repeat(User.NAME_MAX_LENGTH + 1)));
    }

    @Test
    @DisplayName("createdBy 필드는 MAX를 넘어갈 수 없다.")
    void testHobbyValidateLength() {
        assertThrows(IllegalArgumentException.class, () -> createUserWithCreatedBy("*".repeat(User.CREATED_BY_MAX_LENGTH + 1)));
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
        assertThrows(IllegalArgumentException.class, () -> createUser(-1));
    }

}