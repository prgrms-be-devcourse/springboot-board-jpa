package com.prgrms.hyuk.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prgrms.hyuk.domain.post.Content;
import com.prgrms.hyuk.domain.post.Post;
import com.prgrms.hyuk.domain.post.Title;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("연관관계 편의 메서드")
    void testAddPostSuccess() {
        //given
        var user = new User(
            new Name("eunhyuk"),
            new Age(26),
            Hobby.SOCCER);

        var post = new Post(
            new Title("hello world title...."),
            new Content("blah blah...")
        );

        //when
        user.addPost(post);

        //then
        assertAll(
            () -> assertThat(post.getUser()).isEqualTo(user),
            () -> assertThat(user.getPosts().getAllPost().get(0)).isEqualTo(post)
        );
    }
}