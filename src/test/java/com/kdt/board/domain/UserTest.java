package com.kdt.board.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void addPost_정상_Test() {
        //given
        User user = new User();
        user.setAge(27);
        user.setName("Hunki");
        user.setHobby("Game");

        Post post = new Post();
        post.setTitle("Why don't you know?");
        post.setContent("i don't know.");
        //when
        user.addPost(post);
        //then
        assertThat(user.getPosts().get(0).getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    void userAge_비정상_Test() {
        //given
        User user = new User();
        //when //then
        assertThatIllegalArgumentException().isThrownBy(() -> {
            user.setAge(0);
        });
    }
}