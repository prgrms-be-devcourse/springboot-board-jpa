package com.example.springbootboard.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class PostTest {

    @Test
    @DisplayName("Post title은 null이 될 수 없다")
    public void testPostTitleNull() throws Exception {
        //given
        User user = createUser("user", 27, Hobby.SPORTS);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            Post post = Post.createPost(null, "content", user);
        });
    }

    @Test
    @DisplayName("Post content는 null이 될 수 없다")
    public void testPostContentNull() throws Exception {
        //given
        User user = createUser("user", 27, Hobby.SPORTS);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            Post post = Post.createPost(new Title("title"), null, user);
        });
    }

    @Test
    @DisplayName("Post title의 길이는 제한을 넘을 수 없다")
    public void testPostTitleOverLimit() throws Exception {
        //given
        User user = createUser("user", 27, Hobby.SPORTS);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            Post post = Post.createPost(new Title("A".repeat(50)), "content", user);
        });
    }

    @Test
    @DisplayName("Post title의 길이는 0이 될 수 없다")
    public void testPostUserNull() throws Exception {
        //given
        User user = createUser("user", 27, Hobby.SPORTS);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            Post post = Post.createPost(new Title(""), "content", user);
        });
    }

    @Test
    @DisplayName("Post를 생성할 수 있다.")
    public void testCreatePost() throws Exception {
        //given
        User user = createUser("user", 27, Hobby.SPORTS);


        //when
        Post post = Post.createPost(new Title("title"), "content", user);

        //then
        assertThat(post).isNotNull();
    }

    @Test
    @DisplayName("Post의 content를 수정할 수 있다")
    public void testUpdatePost() throws Exception {
        //given
        User user = createUser("user", 27, Hobby.SPORTS);
        Post post = Post.createPost(new Title("title"), "content", user);
        String updateContent = "update content";

        //when
        post.update(post.getTitle(), "update content");

        //then
        assertThat(post.getContent()).isEqualTo(updateContent);
    }

    @Test
    @DisplayName("Post의 title을 수정할 수 있다")
    public void testUpdateTitle() throws Exception {
        //given
        User user = createUser("user", 27, Hobby.SPORTS);
        Post post = Post.createPost(new Title("title"), "content", user);
        String updateTitle = "update title";

        //when
        post.update(new Title(updateTitle), post.getContent());

        //then
        assertThat(post.getTitle()).isEqualTo(new Title(updateTitle));
    }

    private User createUser(String name, int age, Hobby hobby) {
        return User.builder()
                .createdAt(LocalDateTime.now())
                .createdBy(name)
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }


}