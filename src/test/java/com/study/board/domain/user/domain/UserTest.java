package com.study.board.domain.user.domain;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class UserTest {

    @Test
    void 생성_성공_auditing_적용_x() {
        User user = User.create("득윤", Optional.of("체스"));

        assertThat(user.getId()).isNull();
        assertThat(user.getName()).isEqualTo("득윤");
        assertThat(user.getHobby().get()).isEqualTo("체스");
        assertThat(user.getCreatedAt()).isNull();
        assertThat(user.getCreatedBy()).isNull();
    }

    @Test
    void 취미가_없어도_생성성공_auditing_적용_x() {
        User user = User.create("득윤", Optional.empty());

        assertThat(user.getId()).isNull();
        assertThat(user.getName()).isEqualTo("득윤");
        assertThat(user.getHobby()).isEmpty();
        assertThat(user.getCreatedAt()).isNull();
        assertThat(user.getCreatedBy()).isNull();
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 이름이_null_이거나_비어있으면_생성실패(String name) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> User.create(name, Optional.of("체스")));
    }

    @Test
    void 이름의_길이가_50_보다_크면_생성실패() {
        String name = RandomString.make(50 + 1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> User.create(name, Optional.of("내용")));
    }

    @Test
    void 취미의_길이가_50_보다_크면_생성실패() {
        String hobby = RandomString.make(50 + 1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> User.create("득윤", Optional.of(hobby)));
    }

}