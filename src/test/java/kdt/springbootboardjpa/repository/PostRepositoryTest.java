package kdt.springbootboardjpa.repository;

import kdt.springbootboardjpa.respository.PostRepository;
import kdt.springbootboardjpa.respository.UserRepository;
import kdt.springbootboardjpa.respository.entity.Post;
import kdt.springbootboardjpa.respository.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    void testCreatePost(){
        User user = User.builder()
                .name("name")
                .age("34")
                .hobby("hobby")
                .build();
        Post post = Post.builder()
                .title("title")
                .content("content")
                .user(user)
                .build();
        userRepository.save(user);
        postRepository.save(post);

        assertThat(post.getId()).isNotNull();
        assertThat(user.getId()).isNotNull();
        assertThat(post.getCreatedAt()).isNotNull();
        assertThat(post.getCreatedAt()).isInstanceOf(LocalDateTime.class);
        assertThat(post)
                .usingRecursiveComparison()
                .isEqualTo(post);
    }
}
