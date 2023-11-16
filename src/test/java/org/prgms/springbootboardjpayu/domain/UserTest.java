package org.prgms.springbootboardjpayu.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {
    @DisplayName("사용자를 생성할 수 있다.")
    @Test
    void createUser() {
        // given
        String name = "의진";
        Integer age = 23;
        String hobby = "낮잠 자기";

        // when
        User created = User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();

        // then
        assertThat(created.getCratedAt()).isNotNull();
        assertThat(created.getCreatedBy()).isEqualTo(name);
        assertThat(created).extracting("name", "age", "hobby")
                .containsExactly(name, age, hobby);
    }

}