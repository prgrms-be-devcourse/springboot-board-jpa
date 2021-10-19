package com.poogle.board.service.post;

import com.poogle.board.controller.post.PostRequest;
import com.poogle.board.model.post.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostServiceTest {

    @Autowired
    private PostService postService;

    private String title;
    private String content;
    private String writer;

    @BeforeAll
    void setUp() {
        title = "title";
        content = "content";
        writer = "tester";
        postService.write(Post.of(title, content, writer));
    }

    @Test
    @DisplayName("포스트 추가")
    void create_user() {
        Post post = postService.write(Post.of(title, content, writer));
        assertAll(
                () -> assertThat(post, is(notNullValue())),
                () -> assertThat(post.getId(), is(notNullValue())),
                () -> assertThat(post.getTitle(), is(title)),
                () -> assertThat(post.getContent(), is(content))
        );
        log.info("[*] Inserted post: {}", post);
    }

    @Test
    @DisplayName("포스트 id로 조회")
    void find_post_by_id() {
        Post foundPost = postService.findPost(1L).orElse(null);
        assertAll(
                () -> assertThat(foundPost, is(notNullValue())),
                () -> assertThat(foundPost.getId(), is(1L))
        );
        log.info("[*] Found post: {}", foundPost);
    }

    @Test
    @DisplayName("포스트 정보 수정")
    void update_post() {
        Post post = postService.findPost(1L).orElse(null);
        PostRequest newPost = PostRequest.builder()
                .title("new title")
                .content("new content")
                .build();
        assertThat(post, is(notNullValue()));
        postService.modify(1L, newPost);
        assertAll(
                () -> assertThat(post, is(notNullValue())),
                () -> assertThat(post.getId(), is(1L))
        );
        log.info("[*] New post: {}", post);
    }

    @Test
    @DisplayName("포스트 전체 조회")
    void list_posts() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Post> posts = postService.findPosts(pageable);
        assertAll(
                () -> assertThat(posts, is(notNullValue())),
                () -> assertThat(posts.getSize(), is(2))
        );
    }


}
