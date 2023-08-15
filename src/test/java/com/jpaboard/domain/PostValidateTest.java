package com.jpaboard.domain;


import com.jpaboard.post.domain.Post;
import com.jpaboard.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class PostValidateTest {
    @Test
    void title_length_test() {
        String title = "제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목" +
                "제목제목제목제목제목제목제목제목제목제목제목제목제목";
        String cotent = "내용!";
        User user = new User("이름", 10, "쇼핑");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Post(title, cotent, user));
    }

    @Test
    void content_length_test() {
        String title = "제목";
        StringBuilder content = new StringBuilder();

        for (int i = 0; i < 2001; i++) {
            content.append("내용");
        }
        User user = new User("이름", 10, "쇼핑");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Post(title, content.toString(), user));
    }

    @Test
    void update_title_length_test() {
        String title = "제목";
        String cotent = "내용!";
        User user = new User("이름", 10, "쇼핑");
        Post post = new Post(title, cotent, user);

        String updateTitle = "제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목" +
                "제목제목제목제목제목제목제목제목제목제목제목제목제목";
        String updateContent = "수정한 내용!";

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> post.updatePost(updateTitle, updateContent));

    }

    @Test
    void update_content_length_test() {
        String title = "제목";
        String cotent = "내용!";
        User user = new User("이름", 10, "쇼핑");
        Post post = new Post(title, cotent, user);

        String updateTitle = "수정한 제목";
        StringBuilder updateContent = new StringBuilder();
        for (int i = 0; i < 350; i++) {
            updateContent.append("수정한 내용");
        }

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> post.updatePost(updateTitle, updateContent.toString()));

    }
}
