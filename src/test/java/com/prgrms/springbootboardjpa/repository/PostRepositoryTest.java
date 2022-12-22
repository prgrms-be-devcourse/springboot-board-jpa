package com.prgrms.springbootboardjpa.repository;

import com.prgrms.springbootboardjpa.entity.Post;
import com.prgrms.springbootboardjpa.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글을 등록할 수 있다.")
    void testInsert() {
    }

    private Post createPost() {
        Post post = new Post();
        post.setTitle("제목");
        post.setContent("본문");
        post.setUser(createUser());
        post.setCreatedBy(post.getUser().getName());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
    }

    private User createUser() {
        User user = new User();
        user.setName("김창규");
        user.setAge(25);
        user.setHobby("게임");
        user.setCreatedBy("PostRepositoryTest.createUser()");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return user;
    }
}