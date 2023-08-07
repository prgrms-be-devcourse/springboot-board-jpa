package dev.jpaboard.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserTest {

    @DisplayName("유저의 이름과 취미를 수정할 수 있다.")
    @Test
    void updateTest() {
        String 이름 = "이름";
        String 취미 = "취미";

        User user = User.builder()
                .email("qkrdmswl1018@naver.com")
                .password("QWert123?")
                .name("name")
                .age(23)
                .hobby("hobby")
                .build();

        user.update(이름, 취미);

        assertAll(
                () -> assertThat(user.getName()).isEqualTo(이름),
                () -> assertThat(user.getHobby()).isEqualTo(취미));
    }

}
