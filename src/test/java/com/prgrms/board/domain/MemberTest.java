package com.prgrms.board.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class MemberTest {

    @Test
    @DisplayName("name, age 조건을 만족하면 Member가 성공적으로 생성된다.")
    void Member_생성_성공() {
        assertDoesNotThrow(() ->
                Member.builder()
                        .name("kiseo")
                        .age(26)
                        .build());
    }

    @ParameterizedTest
    @DisplayName("Member의 name 필드가 null 또는 공백인 경우 Member 생성에 실패한다.")
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void Member_생성_실패_name_empty(String name) {
        assertThrows(IllegalArgumentException.class, () -> {
            Member.builder()
                    .name(name)
                    .age(26)
                    .build();
        });
    }

    @ParameterizedTest
    @DisplayName("Member의 name 필드의 길이가 2보다 작거나 20보다 큰 경우 Member 생성에 실패한다.")
    @ValueSource(strings = {"a", "김", "asdfasdfasdfasdfasdfasdfasdf", "davidTomJohnsonMichaelJordan" })
    void Member_생성_실패_name_length(String name) {
        assertThrows(IllegalArgumentException.class, () -> {
            Member.builder()
                    .name(name)
                    .age(26)
                    .build();
        });
    }

    @ParameterizedTest
    @DisplayName("Member의 age 필드의 값이 0이하인 경우 Member 생성에 실패한다.")
    @ValueSource(ints = {0, -1, -2, -100, -1000, -123451234})
    void Member_생성_실패_age_minus(int age) {
        assertThrows(IllegalArgumentException.class, () -> {
            Member.builder()
                    .name("giseo")
                    .age(age)
                    .build();
        });
    }
}
