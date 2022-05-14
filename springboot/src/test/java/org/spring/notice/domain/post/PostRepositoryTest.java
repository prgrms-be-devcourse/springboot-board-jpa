package org.spring.notice.domain.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spring.notice.domain.user.User;
import org.spring.notice.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.spring.notice.fixture.Fixture.getUser;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    private Post post1;
    private Post post2;
    private Post post3;
    private Post post4;
    private Post post5;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setup(){
        User savedUser = userRepository.save(getUser());

        post1 = Post.write("테스트1", "테스트 내용", savedUser);
        post2 = Post.write("테스트2", "테스트 내용2", savedUser);
        post3 = Post.write("테스트3", "테스트 내용3", savedUser);
        post4 = Post.write("테스트1", "블라블라블라", savedUser);
        post5 = Post.write("테스트2", "오오홍", savedUser);

        postRepository.saveAll(List.of(post1, post2, post3, post4, post5));
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

    @Test
    void pageFindAllTest() {
        //Given
        // When
        Page<Post> page1 = postRepository.findAll(PageRequest.of(0, 3));
        Page<Post> page2 = postRepository.findAll(PageRequest.of(1, 3));

        // Then
        assertThat(page1.getContent()).containsExactly(post1, post2, post3);
        assertThat(page2.getContent()).containsExactly(post4, post5);
    }
}