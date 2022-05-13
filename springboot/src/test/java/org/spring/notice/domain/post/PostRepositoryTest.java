package org.spring.notice.domain.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spring.notice.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.spring.notice.fixture.Fixture.createUser;

@SpringBootTest
class PostRepositoryTest {

    private final Post post1 = Post.write("테스트1", "테스트 내용", createUser());
    private final Post post2 = Post.write("테스트2", "테스트 내용2", createUser());
    private final Post post3 = Post.write("테스트3", "테스트 내용3", createUser());

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setup(){
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
    }

    @AfterEach
    void tearDown(){
        postRepository.deleteAll();
    }

    @Test
    void findByTitleTest() {
        List<Post> posts = postRepository.findByTitle("테스트1");

        assertThat(posts).containsExactly(post1);
    }

    @Test
    @Transactional
    void getUserFromPost(){
        // Given
        List<Post> posts = postRepository.findByTitle("테스트1");

        // When
        User user = posts.get(0).getUser();

        // Then
        assertThat(user.getName()).isEqualTo(createUser().getName());
        assertThat(user.getAge()).isEqualTo(createUser().getAge());
        assertThat(user.getHobby()).isEqualTo(createUser().getHobby());
    }
}