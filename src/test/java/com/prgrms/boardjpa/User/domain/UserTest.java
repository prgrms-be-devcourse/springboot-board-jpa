package com.prgrms.boardjpa.User.domain;

import com.prgrms.boardjpa.global.exception.InvalidDomainConditionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @ParameterizedTest
    @ValueSource(ints = {-10, -1, -2})
    @DisplayName("유저 생성 실패 - 올바르지 않은 나이")
    void creationFailTest_age(int invalidAge) throws Exception {

        //given -> when -> then
        assertThrows(InvalidDomainConditionException.class, () -> User.builder()
                .name("HONG")
                .age(invalidAge)
                .hobby("코딩")
                .build());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullSource
    @DisplayName("유저 생성 실패 - 올바르지 않은 이름")
    void creationFailTest_name(String invalidName) throws Exception {

        //given -> when -> then
        assertThrows(InvalidDomainConditionException.class, () -> User.builder()
                .name(invalidName)
                .age(10)
                .hobby("코딩")
                .build());
    }
}