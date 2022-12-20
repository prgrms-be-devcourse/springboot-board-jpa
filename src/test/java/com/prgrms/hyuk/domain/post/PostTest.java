package com.prgrms.hyuk.domain.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prgrms.hyuk.domain.user.Age;
import com.prgrms.hyuk.domain.user.Hobby;
import com.prgrms.hyuk.domain.user.Name;
import com.prgrms.hyuk.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post(
            new Title("this is new title..."),
            new Content("blah blah...")
        );
    }

    @Test
    @DisplayName("연관관계 편의 메서드")
    void testAssignUserSuccess() {
        //given
        User user = new User(
            new Name("pang"),
            new Age(26),
            Hobby.SOCCER
        );

        //when
        post.assignUser(user);

        //then
        assertAll(
            () -> assertThat(user.getPosts().getAllPost()).hasSize(1),
            () -> assertThat(post.getUser()).isEqualTo(user)
        );
    }

    @Test
    @DisplayName("게시글 제목과 내용 업데이트 성공")
    void testEditTitleAndContent() {
        //given
        Title title = new Title("this is updated content...");
        Content content = new Content("updated blah blah...");

        //when
        post.editTitleAndContent(
            title,
            content
        );

        //then
        assertAll(
            () -> assertThat(post.getTitle()).isEqualTo(title),
            () -> assertThat(post.getContent()).isEqualTo(content)
        );
    }
}