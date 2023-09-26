package devcource.hihi.boardjpa.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContextPersistenceTest {

    @Autowired
    private EntityManagerFactory emf;

    private static EntityManager entityManager;
    private static EntityTransaction transaction;

    private Post post;
    private User user;

    @BeforeAll
    private void setUp() {
        post = Post.builder()
                .title("hello")
                .content("good")
                .build();

        user = User.builder()
                .name("yejin")
                .age(25)
                .hobby("walking")
                .build();

        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    @AfterEach
    private void clear() {
        entityManager.clear();
    }

    @Test
    @DisplayName("양방향 관계를 저장한다.")
    void save_mapping_test () {
        transaction.begin();


        post.allocateUser(user);

        entityManager.persist(user);

        transaction.commit();
    }

    @Test
    @DisplayName("양방향관계 저장 편의메소드를 테스트한다.")
    void set_method_test() {
        transaction.begin();

        post.allocateUser(user);

        entityManager.persist(user);

        transaction.commit();
    }

    @Test
    @DisplayName("객체그래프탐색을 이용하여 조회한다.")
    void find_object_graph_test() {
        transaction.begin();


        entityManager.persist(post);


        post.allocateUser(user);
        entityManager.persist(user);

        transaction.commit();

        entityManager.clear();

        User findUser = entityManager.find(User.class, user.getId());
        log.info("post-id: {}", findUser.getPostList().get(0).getId());

        Post findPost = entityManager.find(Post.class, findUser.getPostList().get(0).getId());
        log.info("user-name: {}", findPost.getUser().getName());
    }
}

