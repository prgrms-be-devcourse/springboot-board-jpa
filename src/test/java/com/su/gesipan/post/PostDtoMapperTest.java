package com.su.gesipan.post;

import com.su.gesipan.user.User;
import org.junit.jupiter.api.Test;

import static com.su.gesipan.helper.Helper.makeUser;
import static com.su.gesipan.post.PostDtoMapper.toPostEntity;
import static com.su.gesipan.post.PostDtoMapper.toPostResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PostDtoMapperTest {
    @Test
    void toEntity() {
        var user = User.of("su", 24L, "게임");

        var createDto = PostDto.Create.of(user.getId(), "제목", "본문");
        assertThat(createDto).isInstanceOf(PostDto.Create.class);

        var entity = toPostEntity(createDto, user);
        assertThat(entity).isInstanceOf(Post.class);

        assertAll(
                () -> assertThat(entity).hasFieldOrProperty("id"),
                () -> assertThat(entity).hasFieldOrProperty("title"),
                () -> assertThat(entity).hasFieldOrProperty("content"),
                () -> assertThat(entity).hasFieldOrProperty("user")
        );
    }

    @Test
    void toResult() {
        var post = Post.of("제목", "본문", makeUser());
        assertThat(post).isInstanceOf(Post.class);

        var resultDto = toPostResult(post);
        assertThat(resultDto).isInstanceOf(PostDto.Result.class);

        assertAll(
                () -> assertThat(resultDto).hasFieldOrProperty("id"),
                () -> assertThat(resultDto).hasFieldOrProperty("title"),
                () -> assertThat(resultDto).hasFieldOrProperty("content"),
                () -> assertThat(resultDto).hasFieldOrProperty("userId")
        );
    }
}
