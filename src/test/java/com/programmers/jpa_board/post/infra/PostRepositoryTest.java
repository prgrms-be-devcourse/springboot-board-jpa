package com.programmers.jpa_board.post.infra;

import com.programmers.jpa_board.post.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostRepositoryTest {
    @Autowired
    PostRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void 저장_성공() {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("sesada")
                .build();

        //when
        Post saved = repository.save(post);

        //then
        assertThat(saved).usingRecursiveComparison().isEqualTo(post);
    }
}
