package com.kdt.board.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    void saveUser() {
        //given
        User user = new User();
        user.setAge(27);
        user.setName("Hunki");
        user.setHobby("Game");

        Post post = new Post();
        post.setTitle("Why don't you know?");
        post.setContent("i don't know.");
        //when
        post.saveUser(user);
        //then
        assertThat(post.getUser().getName()).isEqualTo(user.getName());
    }
}