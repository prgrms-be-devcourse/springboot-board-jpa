package com.poogle.board.model.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@Slf4j
class UserTest {

    @Test
    @DisplayName("User 생성 테스트")
    void create_valid_user_test() {
        User user = User.of("name", 27, "hobby");
        assertThat(user)
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("age", 27)
                .hasFieldOrPropertyWithValue("hobby", "hobby");
    }

    @Test
    @DisplayName("User 생성 실패 테스트")
    void fail_create_user_test() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> User.of("", 27, "hobby"))
                .withMessage("Name must be provided.");
    }

    @Test
    @DisplayName("User 수정 테스트")
    void update_user_test() {
        User user = User.of("name", 27, "hobby");
        user.update("new name", 30, "new hobby");
        assertThat(user)
                .hasFieldOrPropertyWithValue("name", "new name")
                .hasFieldOrPropertyWithValue("age", 30)
                .hasFieldOrPropertyWithValue("hobby", "new hobby");
    }
}
