package com.example.board.domain.userhobby.entity;

import com.example.board.domain.hobby.entity.Hobby;
import com.example.board.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.board.domain.hobby.entity.HobbyType.GAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserHobbyTest {

    @DisplayName("회원 취미에 회원이 null이면 생성에 실패한다.")
    @Test
    void create_user_hobby_user_null_fail() {
        // given
        final User user = null;
        final Hobby hobby = Hobby.builder()
                .hobbyType(GAME)
                .build();

        // when & then
        assertThatThrownBy(() -> UserHobby.builder()
                .user(user)
                .hobby(hobby)
                .build())
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원 취미에 취미가 null이면 생성에 실패한다.")
    @Test
    void create_user_hobby_hobby_null_fail() {
        // given
        final User user = User.builder()
                .name("박현서")
                .age(3)
                .build();
        final Hobby hobby = null;

        // when & then
        assertThatThrownBy(() -> UserHobby.builder()
                .user(user)
                .hobby(hobby)
                .build())
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원 취미 생성에 성공한다.")
    @Test
    void create_user_hobby_success() {
        // given
        final User user = User.builder()
                .name("박현서")
                .age(3)
                .build();
        final Hobby hobby = Hobby.builder()
                .hobbyType(GAME)
                .build();

        // when
        UserHobby userHobby = UserHobby.builder()
                .user(user)
                .hobby(hobby)
                .build();

        // then
        assertThat(userHobby)
                .hasFieldOrPropertyWithValue("user", user)
                .hasFieldOrPropertyWithValue("hobby", hobby);
    }
}