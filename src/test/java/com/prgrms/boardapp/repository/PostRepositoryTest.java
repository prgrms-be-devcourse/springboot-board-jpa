package com.prgrms.boardapp.repository;

import com.prgrms.boardapp.model.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.prgrms.boardapp.common.PostCreateUtil.createPost;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("정상적인 게시글을 저장할 수 있다.")
    void testSave() {
        Post post = createPost();

        Post savedPost = postRepository.save(post);
        Optional<Post> findPost = postRepository.findById(savedPost.getId());
        assertAll(
                () -> assertThat(findPost).isPresent(),
                () -> assertThat(findPost.get().getId()).isEqualTo(savedPost.getId())
        );
    }

}