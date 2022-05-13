package com.programmers.epicblues.jpa_board.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class EntityJpaTest {

  @Autowired
  EntityManagerFactory entityManagerFactory;

  EntityManager entityManager;

  @BeforeEach
  void setup() {
    entityManager = entityManagerFactory.createEntityManager();
  }

  @AfterEach
  void tearDown() {
    entityManager.close();
  }

  @Test
  @DisplayName("User를 영속 컨텍스트에 보관하면 id를 가져와야 한다.")
  void user_can_be_stored_in_persistence_context() {

    // Given
    User user = User.builder()
        .age(30)
        .hobby("책")
        .createdAt(LocalDateTime.now())
        .name("민성")
        .createdBy("sa").build();

    // When
    entityManager.getTransaction().begin();
    entityManager.persist(user);
    entityManager.getTransaction().commit();
    // Then
    assertThat(user.getId()).isNotNull();
  }

  @Test
  @DisplayName("Post를 영속 컨텍스트에 보관하면 id를 가져와야 한다.")
  void post_can_be_stored_in_persistence_context() {

    // Given
    Post post = Post.builder()
        .title("제목")
        .content("contents!")
        .createdAt(LocalDateTime.now())
        .createdBy("민성")
        .build();
    // When
    entityManager.getTransaction().begin();
    entityManager.persist(post);
    entityManager.getTransaction().commit();
    // Then
    assertThat(post.getId()).isNotNull();
  }
}
