package com.pppp0722.boardjpa.entity;

import com.pppp0722.boardjpa.domain.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository repository;

    @Test
    void save() {

    }
}