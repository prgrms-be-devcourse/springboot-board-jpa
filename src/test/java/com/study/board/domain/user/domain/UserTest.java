package com.study.board.domain.user.domain;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.study.board.domain.user.domain.User.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class UserTest {

    @Test
    void 생성_성공() {
        User user = new User("ndy", "득윤", "체스");

        assertThat(user.getId()).isNull();
        assertThat(user.getName()).isEqualTo("득윤");
        assertThat(user.getHobby()).isEqualTo("체스");
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 로그인_아이디가_null_이거나_비어있으면_생성실패(String loginId) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new User(loginId, "득윤", "체스"));
    }

    @Test
    void 로그인_아이디가의_길이가_제한_보다_크면_생성실패() {
        String loginId = RandomString.make(USER_LOGIN_ID_MAX_LENGTH + 1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new User(loginId, "득윤", "체스"));
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 이름이_null_이거나_비어있으면_생성실패(String name) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new User("ndy", name, "체스"));
    }

    @Test
    void 이름의_길이가_제한_보다_크면_생성실패() {
        String name = RandomString.make(USER_NAME_MAX_LENGTH + 1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new User("ndy", name, "체스"));
    }

    @Test
    void 취미의_길이가_제한_보다_크면_생성실패() {
        String hobby = RandomString.make(USER_HOBBY_MAX_LENGTH + 1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new User("ndy", "득윤", hobby));
    }
}