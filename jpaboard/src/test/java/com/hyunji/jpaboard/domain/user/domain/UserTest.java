package com.hyunji.jpaboard.domain.user.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@Slf4j
class UserTest {

    @Test
    void user_생성_성공() {
        String name = "user01";
        int age = 100;
        String hobby = "hobby";

        User user = User.builder()
                .name(name)
                .age(100)
                .hobby(hobby)
                .build();

        assertThat(user.getId()).isNull();
        assertThat(user.getCreatedAt()).isNull();
        assertThat(user).isNotNull()
                .extracting(User::getName, User::getAge)
                .isEqualTo(List.of(name, age));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void user_name이_공백_혹은_null이면_예외를_던진다(String name) {
        assertThatIllegalArgumentException().isThrownBy(() -> User.builder()
                .name(name)
                .age(100)
                .hobby("hobby")
                .build());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void user_age가_0이하면_예외를_던진다(int age) {
        assertThatIllegalArgumentException().isThrownBy(() -> User.builder()
                .name("user01")
                .age(age)
                .hobby("hobby")
                .build());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void user_hobby가_공백_혹은_null이면_예외를_던진다(String hobby) {
        assertThatIllegalArgumentException().isThrownBy(() -> User.builder()
                .name("user01")
                .age(100)
                .hobby(hobby)
                .build());
    }
}