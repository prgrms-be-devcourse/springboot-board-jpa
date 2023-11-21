package org.prgms.springbootboardjpayu.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @DisplayName("사용자를 생성할 수 있다.")
    @Test
    void createUser() {
        // given
        String name = "의진";
        Integer age = 23;
        String hobby = "낮잠 자기";

        // when
        User savedUser = User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();

        // then
        assertThat(savedUser.getCratedAt()).isNotNull();
        assertThat(savedUser.getCreatedBy()).isEqualTo(name);
        assertThat(savedUser).extracting("name", "age", "hobby")
                .containsExactly(name, age, hobby);
    }

}