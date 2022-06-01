package com.hyunji.jpaboard.domain.post.service;

import com.hyunji.jpaboard.domain.post.domain.Post;
import com.hyunji.jpaboard.domain.user.domain.User;
import com.hyunji.jpaboard.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DefaultPostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Test
    void register() {
        User user = new User("user..", 50, "hobby~");
        userService.register(user);
        Post post = new Post("title...", "content...", user);
        postService.save(post);

        assertThat(post.getId()).isNotNull();
    }

    @Test
    void findPage() {
        User user = new User("user..", 50, "hobby~");
        userService.register(user);
        IntStream.range(1, 51).forEach(i ->
                postService.save(new Post("title" + i, "content" + i, user))
        );

        Page<Post> page = postService.findPage(0);

        assertThat(page).hasSize(10);
        assertThat(page.getTotalElements()).isEqualTo(50);
        assertThat(page.getTotalPages()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
    }

    @Test
    void findById() {
        User user = new User("user..", 50, "hobby~");
        userService.register(user);
        Post post = new Post("title...", "content...", user);
        postService.save(post);

        Post findPost = postService.findPostByIdWithUser(post.getId());

        assertThat(findPost.getId()).isEqualTo(post.getId());
    }
}