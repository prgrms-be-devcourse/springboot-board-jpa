package com.su.gesipan.user;

import org.junit.jupiter.api.Test;

import static com.su.gesipan.user.UserDtoMapper.toUserEntity;
import static com.su.gesipan.user.UserDtoMapper.toUserResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserDtoMapperTest {
    @Test
    void toEntity() {
        var createDto = UserDto.Create.of("su", 24L, "게임하기");
        assertThat(createDto).isInstanceOf(UserDto.Create.class);

        var entity = toUserEntity(createDto);
        assertThat(entity).isInstanceOf(User.class);

        assertAll(
                () -> assertThat(entity).hasFieldOrProperty("id"),
                () -> assertThat(entity).hasFieldOrProperty("name"),
                () -> assertThat(entity).hasFieldOrProperty("age"),
                () -> assertThat(entity).hasFieldOrProperty("hobby"),
                () -> assertThat(entity).hasFieldOrProperty("posts")
        );
    }

    @Test
    void toResult() {
        var user = User.of("su", 24L, "게임하기");
        assertThat(user).isInstanceOf(User.class);

        var resultDto = toUserResult(user);
        assertThat(resultDto).isInstanceOf(UserDto.Result.class);

        assertAll(
                () -> assertThat(resultDto).hasFieldOrProperty("id"),
                () -> assertThat(resultDto).hasFieldOrProperty("name"),
                () -> assertThat(resultDto).hasFieldOrProperty("age"),
                () -> assertThat(resultDto).hasFieldOrProperty("hobby"),
                () -> assertThat(resultDto).hasFieldOrProperty("posts")
        );
    }
}
