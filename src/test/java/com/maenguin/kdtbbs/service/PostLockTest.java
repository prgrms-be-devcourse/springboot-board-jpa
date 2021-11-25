package com.maenguin.kdtbbs.service;

import com.maenguin.kdtbbs.controller.OptimisticLockTryer;
import com.maenguin.kdtbbs.domain.Post;
import com.maenguin.kdtbbs.domain.User;
import com.maenguin.kdtbbs.dto.PostDto;
import com.maenguin.kdtbbs.repository.PostRepository;
import com.maenguin.kdtbbs.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@Slf4j
class PostLockTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private OptimisticLockTryer optimisticLockTryer;

    @BeforeEach
    void setup() {
        User user = new User("maeng", "piano");
        Post post = new Post("hello", "world");
        user.addPost(post);
        userRepository.save(user);
    }

    @Test
    @DisplayName("조회수 증가 테스트")
    void test2() throws Exception {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                PostDto attempt = optimisticLockTryer.attempt(() -> postService.getPostById(2L), 10);
            });
        }
        Thread.sleep(1000);
        Optional<Post> post = postRepository.findById(2L);
        assertThat(post.isPresent(), is(true));
        assertThat(post.get().getView(), is(10L));
    }
}
