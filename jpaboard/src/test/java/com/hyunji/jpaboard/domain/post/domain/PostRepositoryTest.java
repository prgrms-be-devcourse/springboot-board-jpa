package com.hyunji.jpaboard.domain.post.domain;

import com.hyunji.jpaboard.domain.user.domain.User;
import com.hyunji.jpaboard.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 상태저장_테스트() {
        User user = new User("user01", 10, "hobby");
        userRepository.save(user);
        Post post = new Post("title01", "content...", user);

        postRepository.save(post);

        Optional<Post> byId = postRepository.findById(post.getId());
        assertThat(byId).isNotEmpty();
        Post saved = byId.get();
        assertThat(saved.getId()).isEqualTo(post.getId());
        assertThat(saved.getUser().getId()).isEqualTo(user.getId());
        assertThat(saved.getTitle()).isEqualTo(post.getTitle());
        assertThat(saved.getContent()).isEqualTo(post.getContent());
        assertThat(saved.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}