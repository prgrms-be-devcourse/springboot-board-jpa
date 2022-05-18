package com.prgrms.hyuk.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.hyuk.domain.post.Content;
import com.prgrms.hyuk.domain.post.Post;
import com.prgrms.hyuk.domain.post.Title;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostsTest {

    private Posts posts;

    @BeforeEach
    void setUp() {
        posts = new Posts();
    }

    @Test
    @DisplayName("post 추가")
    void testAddSuccess() {
        //given
        //when
        posts.add(new Post(
            new Title("this is test title.."),
            new Content("content")));

        //then
        assertThat(posts.getAllPost()).hasSize(1);
    }

    @Test
    @DisplayName("post 삭제")
    void testRemovePost() {
        //given
        var post = new Post(
            new Title("this is test title.."),
            new Content("content"));
        posts.add(post);

        //when
        posts.remove(post);

        //then
        assertThat(posts.getAllPost()).isEmpty();
    }
}