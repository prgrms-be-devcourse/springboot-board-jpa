package org.prgrms.myboard.repository;

import org.junit.jupiter.api.*;
import org.prgrms.myboard.domain.Post;
import org.prgrms.myboard.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableJpaAuditing
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestEntityManager entityManager;
    private User user;
    private Post post;

    @BeforeAll
    void setup() {
        user = User.builder()
            .name("koo")
            .age(23)
            .build();
    }

    @BeforeEach
    void setupPost() {
        post = new Post("testPost", "testPostContent");
    }

    @AfterEach
    void cleanup() {
        entityManager.clear();
    }

    @DisplayName("postRepository와 entityManager는 Null이 아니다.")
    @Test
    void context_test() {
        assertNotNull(postRepository);
        assertNotNull(entityManager);
    }

    @DisplayName("Id로 게시글을 찾을 수 있다.")
    @Test
    // TODO : 이거 왜 db 저장 안되냐?
    // TODO : user persist 한번만 할 순 없나.
    void find_by_id_test() {
        // given
        entityManager.persist(user);
//        Post post = new Post("testPost", "testPostContent");
        post.allocateUser(user);

        // when
        postRepository.save(post);
//        entityManager.clear();
        Optional<Post> retrievedPost = postRepository.findById(post.getId());

        // then
        assertThat(retrievedPost).contains(post);
    }

    @DisplayName("게시글을 저장할 수 있다.")
    @Test
    void insert_post_test() {
        // given
        entityManager.persist(user);
        post.allocateUser(user);

        // when
        // 여기서 1차캐싱만 이루어지고, 실제 DB는 저장 x.
        postRepository.save(post);
        Post retrievedPost = entityManager.find(Post.class, post.getId());

        // then
        assertThat(post).usingRecursiveComparison().isEqualTo(retrievedPost);
    }

    // 이거 영속성 콘텍스트 안에서만 데이터가 노는데 괜찮나 ?
    @DisplayName("게시글을 업데이트할 수 있다.")
    @Test
    void update_post_test() {
        // given
        entityManager.persist(user);
        post.allocateUser(user);
        postRepository.save(post);

        // when
        post.changeContent("updateContent");
        postRepository.save(post);

        // then
        Post retrievedPost = entityManager.find(Post.class, post.getId());
        assertThat(post).usingRecursiveComparison().isEqualTo(retrievedPost);
    }

    @DisplayName("게시글을 삭제할 수 있다.")
    @Test
    void delete_post_test() {
        // given
        entityManager.persist(user);
        post.allocateUser(user);
        postRepository.save(post);

        // when
        postRepository.delete(post);

        // then
        assertNull(entityManager.find(Post.class, post.getId()));
    }

    @DisplayName("작성자로 게시글을 찾을 수 있다.")
    @Test
    void find_by_created_by_test() {
        // given
        entityManager.persist(user);
        post.allocateUser(user);
        postRepository.save(post);

        // when
        List<Post> userPosts = postRepository.findAllByCreatedBy(user.getName());

        // then
        assertThat(userPosts).containsExactlyInAnyOrderElementsOf(user.getPosts());
    }
}