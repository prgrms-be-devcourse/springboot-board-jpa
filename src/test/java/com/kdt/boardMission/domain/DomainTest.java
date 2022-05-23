package com.kdt.boardMission.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DomainTest {

    @Test
    public void 유저생성_및_슈퍼클래스_상속_확인() throws Exception {

        //given
        User user = new User("name", 10, "hhhho");

        //when

        //then
        LocalDateTime createdAt = user.getCreatedAt();
        assertThat(createdAt).isNotNull();
        assertThat(user.getCreatedBy()).isNull();
    }

    @Test
    public void 포스트_생성_및_유저와의_연관관계_메서드_동작확인() throws Exception {

        //given
        User user = new User("name", 10, "hhhho");

        //when
        Post post = new Post(user, "title", "content");

        //then
        assertThat(user.getPosts()).contains(post);
    }

    @Test
    public void 포스트_삭제_메서드_동작확인() throws Exception {

        //given
        User user = new User("name", 10, "hhhho");
        Post post = new Post(user, "title", "content");

        //when
        post.deletePost();

        //then
        assertThat(user.getPosts().contains(post)).isFalse();
    }


}