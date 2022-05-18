package com.prgrms.hyuk.domain.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prgrms.hyuk.domain.user.Age;
import com.prgrms.hyuk.domain.user.Hobby;
import com.prgrms.hyuk.domain.user.Name;
import com.prgrms.hyuk.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    @DisplayName("연관관계 편의 메서드")
    void testAssignUserSuccess() {
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
        post.assignUser(user);

        //then
        assertAll(
            () -> assertThat(user.getPosts().getAllPost()).hasSize(1),
            () -> assertThat(post.getUser()).isEqualTo(user)
        );
    }
}