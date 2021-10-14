package com.example.jpaboard.repository;

import static org.assertj.core.api.Assertions.*;

import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.infra.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    TestEntityManager em;

    @Test
    void findById_returnsPost() {
        Post savedPost = em.persistFlushFind(new Post("test title", "test content"));
        Post findPost = postRepository.findById(savedPost.getId()).orElseThrow();

        assertThat(findPost.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(findPost.getContent()).isEqualTo(savedPost.getContent());
    }
}