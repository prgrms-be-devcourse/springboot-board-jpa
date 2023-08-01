package com.kdt.devcourse.module.post.domain.repository;

import com.kdt.devcourse.module.post.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Post post;

    @BeforeEach
    void initPost() {
        post = postRepository.save(new Post("", ""));
    }

    @Test
    @DisplayName("변경 감지를 통해서 값을 변경할 수 있어야 한다.")
    void update_Success() {
        // given

        // when
        post.updateTitleAndContent("newTitle", "newContent");
        entityManager.flush();

        // then
        Optional<Post> optionalPost = postRepository.findById(post.getId());
        assertThat(optionalPost).isPresent();
        assertThat(optionalPost.get()).usingRecursiveComparison().isEqualTo(post);
    }
}
