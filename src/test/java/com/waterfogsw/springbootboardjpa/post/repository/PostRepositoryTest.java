package com.waterfogsw.springbootboardjpa.post.repository;

import com.waterfogsw.springbootboardjpa.post.entity.Post;
import com.waterfogsw.springbootboardjpa.user.entity.User;
import com.waterfogsw.springbootboardjpa.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    private User writer;

    @BeforeEach
    void setUp() {
        final var user = User.builder()
                .name("test")
                .email("test@naver.com")
                .build();

        writer = userRepository.save(user);
    }

    @Test
    @Order(1)
    @DisplayName("findById 쿼리 테스트")
    public void findById() {
        //given
        final var testPost = generateTestPost("test", "test", writer);
        postRepository.save(testPost);

        //when
        final var foundPost = postRepository.findById(1L);

        //then
        assertTrue(foundPost.isPresent());
    }

    @Test
    @Order(2)
    @DisplayName("findWithPagination 쿼리 테스트")
    public void findWithPagination() {
        //given
        final var testPost1 = generateTestPost("test", "test", writer);
        final var testPost2 = generateTestPost("test", "test", writer);

        postRepository.save(testPost1);
        postRepository.save(testPost2);

        final var pageRequest = PageRequest.of(0, 10);

        //when
        final var posts = postRepository.findWithPagination(pageRequest);

        //then
        assertEquals(2, posts.size());
        assertTrue(em.contains(posts.get(0).getUser()));
        assertTrue(em.contains(posts.get(1).getUser()));
    }

    private Post generateTestPost(String title, String content, User writer) {
        final var newPost = Post.builder()
                .title(title)
                .content(content)
                .build();

        newPost.updateAuthor(writer);
        return newPost;
    }
}