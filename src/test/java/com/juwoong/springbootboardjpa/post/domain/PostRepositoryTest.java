package com.juwoong.springbootboardjpa.post.domain;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import com.juwoong.springbootboardjpa.config.JpaConfig;
import com.juwoong.springbootboardjpa.post.domain.repository.PostRepository;
import com.juwoong.springbootboardjpa.user.domain.User;

@DataJpaTest
@Import(JpaConfig.class)
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("Post 엔티티 저장에 성공한다.")
    void post_save_success() {
        // given
        User user = User.builder()
            .name("JUWOONG")
            .age(29)
            .hobby("TRAVEL")
            .build();

        Post post = Post.builder()
            .user(user)
            .title("test Titles")
            .content("sdfdkf  dlka flkdjflkasjfnl fdlfkjf lkljlkjgflk")
            .build();

        // when
        Post savedPost = postRepository.save(post);

        // then
        assertThat(post).usingRecursiveComparison().isEqualTo(savedPost);
        assertThat(user).usingRecursiveComparison().isEqualTo(savedPost.getUser());
    }
}