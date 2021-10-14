package com.example.springbootboard.repository;

import com.example.springbootboard.domain.Hobby;
import com.example.springbootboard.domain.Post;
import com.example.springbootboard.domain.User;
import com.example.springbootboard.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@Slf4j
@SpringBootTest
public class PostRepostioryTest {
    @Autowired
    private PostRepository postRepository;

    private User newUser;

    @BeforeEach
    void setup() {
        newUser = User.of(UUID.randomUUID().toString(), "sezi", 29, Hobby.FOOD);
    }

    @Test
    void saveTest() {
        // given
        final String title = "base title";
        final String content = "base content";
        Post post = new Post(title, content, newUser);

        // when
        postRepository.save(post);
        List<Post> allByKeyword = postRepository.findAllByKeyword(post.getTitle());

        // then
        assertThat(allByKeyword.size(), is(1));
        Post retrievedPost = allByKeyword.get(0);
        assertThat(retrievedPost.getTitle(), equalTo(title));
        assertThat(retrievedPost.getContent(), equalTo(content));
    }
}
