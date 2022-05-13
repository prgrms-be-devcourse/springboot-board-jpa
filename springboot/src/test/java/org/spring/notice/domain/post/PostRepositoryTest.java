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
@Transactional
class PostRepositoryTest {

    private final Post post1 = Post.write("테스트1", "테스트 내용", createUser());
    private final Post post2 = Post.write("테스트2", "테스트 내용2", createUser());
    private final Post post3 = Post.write("테스트3", "테스트 내용3", createUser());
    private final Post post4 = Post.write("테스트1", "블라블라블라", createUser());
    private final Post post5 = Post.write("테스트2", "오오홍", createUser());

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setup(){
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
    }

    @Test
    void findByTitleTest() {
        //Given
        // When
        List<Post> test1_posts = postRepository.findByTitle("테스트1");
        List<Post> test2_posts = postRepository.findByTitle("테스트2");

        // Then
        assertThat(test1_posts).hasSize(2);
        assertThat(test1_posts).containsExactlyInAnyOrder(post1, post4);
        assertThat(test2_posts).hasSize(2);
        assertThat(test2_posts).containsExactlyInAnyOrder(post2, post5);
    }
}