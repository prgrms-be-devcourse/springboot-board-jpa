package org.spring.notice.domain.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@SpringBootTest
class UserTest {

    @ParameterizedTest
    @NullAndEmptySource
    void 이름_공백x(String name) {
        assertThatIllegalArgumentException().isThrownBy(
                () -> User.create(
                        UUID.randomUUID().toString(),
                        name, 10, "공놀이")
        );
    }

    @Test
    void 나이_음수x() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> User.create(
                        UUID.randomUUID().toString(), "name",
                        -2, "공놀이")
        );
    }

    @Test
    void 이름_길이제한() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> User.create(
                        UUID.randomUUID().toString(), "a".repeat(User.NAME_MAX_LENGTH + 1),
                        10, "공놀이")
        );
    }
    @Test
    void 취미_길이제한() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> User.create(
                        UUID.randomUUID().toString(), "name",
                        3, "a".repeat(User.HOBBY_MAX_LENGTH + 1))
        );
    }

    @Test
    void 유효하지않은_uuid() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> User.create(
                        "abc", "name",
                        3, "취미")
        );
    }
}