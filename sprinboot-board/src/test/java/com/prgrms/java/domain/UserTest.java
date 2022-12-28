package com.prgrms.java.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @DisplayName("사용자의 이름은 30글자를 초과할 수 없다.")
    @Test
    void nameOver30() {
        // when then
        Assertions.assertThatThrownBy(() -> new User("Name must be less than or equal to 30 characters.", 26, HobbyType.MOVIE)).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("사용자의 이름은 빈 문자열이 될 수 없다.")
    @Test
    void nameEmpty() {
        // when then
        Assertions.assertThatThrownBy(() -> new User("", 26, HobbyType.MOVIE)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new User(null, 26, HobbyType.MOVIE)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("사용자의 나이는 1보다 작을 수 없다.")
    @Test
    void ageUnder1() {
        // when then
        Assertions.assertThatThrownBy(() -> new User("taek", 0, HobbyType.MOVIE)).isInstanceOf(IllegalStateException.class);
    }
}