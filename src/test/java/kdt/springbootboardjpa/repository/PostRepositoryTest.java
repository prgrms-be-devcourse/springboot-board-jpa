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
        User savedUser = userRepository.save(user);
        Post savedPost = postRepository.save(post);

        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedPost.getCreatedAt()).isNotNull();
        assertThat(savedPost.getCreatedAt()).isInstanceOf(LocalDateTime.class);
        assertThat(savedPost)
                .usingRecursiveComparison()
                .isEqualTo(post);
    }
}
