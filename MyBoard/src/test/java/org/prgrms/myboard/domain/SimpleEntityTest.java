package org.prgrms.myboard.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// created_at, updated_at 자동설정위한 어노테이션.
@EnableJpaAuditing
public class SimpleEntityTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private static EntityManager entityManager;
    private static EntityTransaction entityTransaction;

    @BeforeAll
    void setup() {
        entityManager = entityManagerFactory.createEntityManager();;
        entityTransaction = entityManager.getTransaction();
    }

    @AfterEach
    void clearup() {
        entityManager.clear();
    }

    @DisplayName("유저를 생성할 수 있다.")
    @Test
    void insert_user_test() {
        // given
        entityTransaction.begin();
        User user = User.builder()
            .name("구범모")
            .age(21)    
            .build();

        // when
        entityManager.persist(user);

        // then
        entityTransaction.commit();
        User retrievedUser = entityManager.find(User.class, user.getId());
        assertThat(user).usingRecursiveComparison().isEqualTo(retrievedUser);
    }

    @DisplayName("게시글을 올릴 수 있다.")
    @Test
    void insert_board_test() {
        // given
        entityTransaction.begin();
        User user = User.builder()
            .age(27)
            .name("구범모형")
            .hobby("구범모랑놀기")
            .build();
        Post post = new Post("testBoard", "this_is_test", user);

        // when
        entityManager.persist(user);

        // then
        entityTransaction.commit();
        Post retrievedPost = entityManager.find(Post.class, post.getId());
        assertThat(post).usingRecursiveComparison().isEqualTo(retrievedPost);
    }

    @DisplayName("게시글은 작성자가 있어야 한다.")
    @Test
    void board_must_have_user() {
        // given
        entityTransaction.begin();
        User user = User.builder()
            .name("구범모")
            .age(21)
            .build();
        Post post = new Post("No_author_board", "No_author_board", user);

        // when
        entityManager.persist(post);

        // then
        assertThrows(ConstraintViolationException.class,
            () -> entityManager.persist(post));
        entityTransaction.commit();
    }
}
